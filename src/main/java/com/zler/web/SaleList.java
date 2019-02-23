package com.zler.web;

import com.zler.domain.SaleInfo;
import com.zler.factory.BasicFactory;
import com.zler.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SaleList")
public class SaleList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用Service中查询销售榜单的方法
        OrderService service = BasicFactory.getFacroty().getService(OrderService.class);
        List<SaleInfo> list = service.saleList();
        //2.将销售榜单信息组织成csv格式的数据
        StringBuffer buffer = new StringBuffer();
        buffer.append("商品编号,商品名称,销售数量\r\n");
        for(SaleInfo si : list){
            buffer.append(si.getId()+","+si.getName()+","+si.getSalenum()+"\r\n");
        }
        String data = buffer.toString();
        //3.提供下载
        String filename = "Estore销售榜单_" + new Date().toLocaleString()+".csv";
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
        response.setContentType(this.getServletContext().getMimeType(filename));
        response.getWriter().write(data);
    }
}
