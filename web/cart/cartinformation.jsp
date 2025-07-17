<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đơn hàng của bạn</title>
        <style>
            <%-- Header giống cart.jsp --%>
            .header_top {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 30px;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                font-size: 14px;
            }
            .header_main {
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 15px 30px;
                background-color: white;
                border-bottom: 2px solid #e0e0e0;
            }
            .header_logo img {
                height: 80px;
                width: auto;
            }
            .header_logo_with_title {
                display: flex;
                align-items: center;
            }
            .name_page {
                display: flex;
                align-items: center;
                font-size: 24px;
                font-weight: 300;
                font-family: 'Segoe UI', 'Helvetica Neue', Arial, sans-serif;
                color: #27c6ee;
                margin-left: 10px;
            }
            .separator {
                margin-right: 8px;
                font-size: 40px;
                color: #27c6ee;
                font-weight: 100;
            }
            .title-text {
                color: #27c6ee;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
            }
            .container {
                max-width: 1000px;
                margin: 30px auto;
                background: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            h2 {
                color: #27c6ee;
                margin-bottom: 20px;
                text-align: center;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: center;
            }
            th {
                background-color: #f0f8ff;
            }
            .status {
                font-weight: bold;
            }
            .status.pending {
                color: orange;
            }
            .status.shipped {
                color: blue;
            }
            .status.received {
                color: green;
            }
            .action-btn {
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
                transition: 0.3s;
            }
            .action-btn:hover {
                background: linear-gradient(90deg, #1a7a8f, #5cc5e0);
            }
        </style>
    </head>
    <body>

        <%-- HEADER giống cart.jsp --%>
        <div class="header_top">
            <div class="header_contact">Hotline: <strong>0938 424 289</strong></div>
            <div class="header_account">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span class="header_login">${sessionScope.user.name}</span>
                        <a class="header_login" href="${pageContext.request.contextPath}/logout">| Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a class="header_login" href="${pageContext.request.contextPath}/login">Đăng Nhập</a>
                        <a class="header_login" href="${pageContext.request.contextPath}/register">| Đăng ký</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="header_main">
            <div class="header_logo_with_title">
                <a class="header_logo" href="${pageContext.request.contextPath}/home">
                    <img src="${pageContext.request.contextPath}/image/logo1.jpg" alt="Logo BookZone">
                </a>
                <div class="name_page">
                    <span class="separator">|</span>
                    <span class="title-text">Đơn hàng của bạn</span>
                </div>
            </div>
        </div>

        <%-- NỘI DUNG TRANG --%>
        <div class="container">
            <h2>Danh sách đơn hàng đã đặt</h2>

            <c:if test="${empty orders}">
                <p style="text-align: center;">Bạn chưa có đơn hàng nào.</p>
            </c:if>

            <c:if test="${not empty orders}">
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn</th>
                            <th>Ngày đặt</th>
                            <th>Giá trị</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td>${order.id}</td>
                                <td><fmt:formatDate value="${order.createdAt}" pattern="HH:mm dd/MM/yyyy" /></td>
                                <td><fmt:formatNumber value="${order.totalPrice}" type="number" /> ₫</td>
                                <td>
                                    <span class="status ${order.status == 'đang xử lý' ? 'pending' : order.status == 'đang vận chuyển' ? 'shipped' : 'received'}">
                                        ${order.status}
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.status == 'đang vận chuyển'}">
                                            <form method="post" action="${pageContext.request.contextPath}/updatestatus">
                                                <input type="hidden" name="orderId" value="${order.id}" />
                                                <button class="action-btn" type="submit">Đã nhận hàng</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${order.status == 'đã nhận'}">
                                            <form method="post" action="${pageContext.request.contextPath}/deleteorder" onsubmit="return confirm('Bạn có chắc muốn xóa đơn này không?');">
                                                <input type="hidden" name="orderId" value="${order.id}" />
                                                <button class="action-btn" style="background: crimson;">Xóa</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <span>--</span>
                                        </c:otherwise>
                                    </c:choose>

                                </td>
                            </tr>

                            <!-- Hiển thị danh sách sách trong đơn hàng -->
                            <tr>
                                <td colspan="5">
                                    <div style="padding: 10px;">
                                        <c:forEach var="detail" items="${orderDetailsMap[order.id]}">
                                            <div style="display: flex; align-items: center; margin-bottom: 10px;">
                                                <img src="${detail.bookImage}" width="60" height="80" style="margin-right: 15px;" />

                                                <div>
                                                    <strong>${detail.bookTitle}</strong><br />
                                                    Số lượng: ${detail.quantity} – Giá: 
                                                    <fmt:formatNumber value="${detail.price}" type="number" /> ₫
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </c:if>
        </div>
        <jsp:include page="/footer.jsp" />
    </body>
</html>
