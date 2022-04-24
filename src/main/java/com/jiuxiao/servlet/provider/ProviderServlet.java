package com.jiuxiao.servlet.provider;

import com.alibaba.fastjson.JSONArray;
import com.jiuxiao.pojo.Provider;
import com.jiuxiao.pojo.User;
import com.jiuxiao.service.provider.ProviderService;
import com.jiuxiao.service.provider.ProviderServiceImpl;
import com.jiuxiao.utils.Constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 供应商 Servlet
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 20:54
 * @since 1.0.0
 */
public class ProviderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("query")) {
            this.query(req, resp);
        } else if (method != null && method.equals("add")) {
            this.add(req, resp);
        } else if (method != null && method.equals("view")) {
            this.getProviderById(req, resp, "providerview.jsp");
        } else if (method != null && method.equals("modify")) {
            this.getProviderById(req, resp, "providermodify.jsp");
        } else if (method != null && method.equals("modifysave")) {
            this.modify(req, resp);
        } else if (method != null && method.equals("delprovider")) {
            this.delProvider(req, resp);
        }
    }

    /**
     * 查询供应商信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryProName = request.getParameter("queryProName");
        String queryProCode = request.getParameter("queryProCode");

        if (StringUtils.isNullOrEmpty(queryProName)) {
            queryProName = "";
        }
        if (StringUtils.isNullOrEmpty(queryProCode)) {
            queryProCode = "";
        }

        List<Provider> providerList;
        ProviderService providerService = new ProviderServiceImpl();
        providerList = providerService.getProviderList(queryProName, queryProCode);
        request.setAttribute("providerList", providerList);
        request.setAttribute("queryProName", queryProName);
        request.setAttribute("queryProCode", queryProCode);
        request.getRequestDispatcher("providerlist.jsp").forward(request, response);
    }

    /**
     * 增加供应商
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String proCode = request.getParameter("proCode");
        String proName = request.getParameter("proName");
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProFax(proFax);
        provider.setProAddress(proAddress);
        provider.setProDesc(proDesc);
        provider.setCreatedBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        boolean flag = false;
        ProviderService providerService = new ProviderServiceImpl();
        flag = providerService.add(provider);

        if (flag) {
            response.sendRedirect(request.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            request.getRequestDispatcher("provideradd.jsp").forward(request, response);
        }
    }

    /**
     * 删除供应商
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void delProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("proid");
        HashMap<String, String> resultMap = new HashMap<String, String>();

        if (!StringUtils.isNullOrEmpty(id)) {
            ProviderService providerService = new ProviderServiceImpl();
            int flag = providerService.deleteProviderById(id);
            if (flag == 0) {          //删除成功
                resultMap.put("delResult", "true");
            } else if (flag == -1) {   //删除失败
                resultMap.put("delResult", "false");
            } else if (flag > 0) {     //该供应商下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        } else {
            resultMap.put("delResult", "notexit");
        }

        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 修改供应商信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");
        String id = request.getParameter("id");

        Provider provider = new Provider();
        provider.setId(Integer.valueOf(id));
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProFax(proFax);
        provider.setProAddress(proAddress);
        provider.setProDesc(proDesc);
        provider.setModifyBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());

        boolean flag = false;
        ProviderService providerService = new ProviderServiceImpl();
        flag = providerService.modify(provider);

        if (flag) {
            response.sendRedirect(request.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            request.getRequestDispatcher("providermodify.jsp").forward(request, response);
        }
    }

    /**
     * 根据 ID 获取供应商
     *
     * @param request
     * @param response
     * @param url
     * @throws ServletException
     * @throws IOException
     */
    private void getProviderById(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        String id = request.getParameter("proid");

        if (!StringUtils.isNullOrEmpty(id)) {
            ProviderService providerService = new ProviderServiceImpl();
            Provider provider = null;
            provider = providerService.getProviderById(id);
            request.setAttribute("provider", provider);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
