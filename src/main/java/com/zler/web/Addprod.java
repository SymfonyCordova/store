package com.zler.web;

import com.zler.domain.Product;
import com.zler.factory.BasicFactory;
import com.zler.service.ProdService;
import com.zler.tool.IOToolkit;
import com.zler.tool.PicToolkit;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "Addprod")
public class Addprod extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFacroty().getService(ProdService.class);
        try {
            String encode = this.getServletContext().getInitParameter("encode");
            Map<String, String> paramMap= new HashMap<>();
            //1.上传图片
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*100);
            factory.setRepository(new File(this.getServletContext().getRealPath("WEB-INF/temp")));

            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding(encode);
            fileUpload.setFileSizeMax(1024*1024*1);
            fileUpload.setSizeMax(1024*1024*10);

            if(!fileUpload.isMultipartContent(request)){
                throw new RuntimeException("请使用正确的表单进行上传!");
            }

            List<FileItem> list = fileUpload.parseRequest(request);
            for(FileItem item : list){
                if(item.isFormField()){
                    //普通字段
                    String name = item.getFieldName();
                    String value = item.getString(encode);
                    paramMap.put(name, value);
                }else{
                    //文件上传
                    String realname = item.getName();
                    String uuidname = UUID.randomUUID().toString()+"_"+realname;

                    String hash = Integer.toHexString(uuidname.hashCode());
                    String upload = this.getServletContext().getRealPath("/WEB-INF/upload");
                    String imgurl = "/WEB-INF/upload";
                    for(char c: hash.toCharArray()){
                        upload += "/"+c;
                        imgurl += "/"+c;
                    }
                    imgurl += "/" + uuidname;
                    paramMap.put("imgurl", imgurl);

                    File uploadFile = new File(upload);
                    if(!uploadFile.exists()){
                        uploadFile.mkdirs();
                    }

                    InputStream in = item.getInputStream();
                    OutputStream out = new FileOutputStream(new File(upload,uuidname));

                    IOToolkit.In2Out(in, out);
                    IOToolkit.close(in, out);

                    item.delete();

                    //--生成缩略图
                    //PicToolkit picToolkit = new PicToolkit(this.getServletContext().getRealPath(imgurl));
                    //picToolkit.resizeByHeight(140);
                }
            }

            //2.调用Service中提供的方法,在数据库中添加商品
            Product prod = new Product();
            BeanUtils.populate(prod, paramMap);
            service.addProd(prod);

            //3.提示成功,回到主页
            response.getWriter().write("添加商品成功！3秒回到主页...");
            response.setHeader("Refresh", "3;url=/index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
