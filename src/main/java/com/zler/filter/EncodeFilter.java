package com.zler.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@javax.servlet.annotation.WebFilter(filterName = "EncodeFilter")
public class EncodeFilter implements javax.servlet.Filter {
    private FilterConfig config = null;
    private ServletContext context = null;
    private String encode = null;

    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        //--响应乱码的解决
        resp.setCharacterEncoding(this.encode);
        resp.setContentType("text/html;charset=" + this.encode);
        //--利用装饰设计模式改变request对象获取请求参数相关的方法,从而解决请求参数乱码
        chain.doFilter(new MyHttpServletRequest((HttpServletRequest) req), resp);
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {
        this.config = config;
        this.context = config.getServletContext();
        this.encode = context.getInitParameter("encode");
    }

    private class MyHttpServletRequest extends HttpServletRequestWrapper{
        private HttpServletRequest request = null;
        private boolean isNotEncode = true;

        public MyHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            try{
                if(request.getMethod().equalsIgnoreCase("POST")){
                    request.setCharacterEncoding(encode);
                    return request.getParameterMap();
                }else if(request.getMethod().equalsIgnoreCase("GET")){
                    Map<String, String[]> map = request.getParameterMap();
                    if(this.isNotEncode) {
                        for (Map.Entry<String, String[]> entry : map.entrySet()) {
                            String[] vs = entry.getValue();
                            for (int i = 0; i < vs.length; ++i) {
                                vs[i] = new String(vs[i].getBytes("ISO8859-1"), encode);
                            }
                        }
                        this.isNotEncode = false;
                    }
                    return map;
                }else{
                    return request.getParameterMap();
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        @Override
        public String[] getParameterValues(String name) {
            return this.getParameterMap().get(name);
        }

        @Override
        public String getParameter(String name) {
            return this.getParameterValues(name) == null ? null : getParameterValues(name)[0];
        }
    }
}
