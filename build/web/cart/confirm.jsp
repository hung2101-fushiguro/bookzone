<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Xác nhận đơn hàng</title>
        <style>
            /* CSS chung cho header (giống cart.jsp) */
            .header_top {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 30px;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                font-size: 14px;
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
            .header_top a {
                color: white;
                text-decoration: none;
                margin-left: 15px;
            }
            .header_top a:hover {
                text-decoration: underline;
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

            /* CSS riêng cho trang confirm */
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
            }
            .container {
                max-width: 600px;
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
            label {
                font-weight: bold;
                display: block;
                margin-top: 15px;
                color: #555;
            }
            input[type="text"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
            }
            .buttons {
                margin-top: 25px;
                text-align: center;
            }
            button {
                padding: 12px 25px;
                margin: 0 10px;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                border: none;
                border-radius: 4px;
                font-weight: bold;
                cursor: pointer;
                font-size: 16px;
                transition: all 0.3s;
            }
            button:hover {
                background: linear-gradient(90deg, #1a7a8f, #5cc5e0);
                transform: translateY(-2px);
            }
            .info {
                background: #f8f9fa;
                padding: 20px;
                margin-top: 25px;
                border-radius: 5px;
                border-left: 4px solid #27c6ee;
            }
            .info p {
                margin: 8px 0;
                color: #333;
            }
            .info strong {
                color: #27c6ee;
            }
            .error-message {
                color: #d70018;
                font-weight: bold;
                text-align: center;
                margin: 15px 0;
                padding: 10px;
                background-color: #ffecec;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <%-- HEADER GIỐNG CART.JSP --%>
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
                    <img src="image/logo1.jpg" alt="Logo BookZone">
                </a>
                <div class="name_page">
                    <span class="separator">|</span>
                    <span class="title-text">Xác nhận đơn hàng</span>
                </div>
            </div>
        </div>

        <%-- NỘI DUNG CHÍNH --%>
        <div class="container">
            <h2>Xác nhận thông tin đơn hàng</h2>

            <c:if test="${not empty requestScope.error}">
                <div class="error-message">${requestScope.error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/update-contact" method="post">
                <label for="address">Địa chỉ nhận hàng:</label>
                <input type="text" id="address" name="address" 
                       value="${sessionScope.user.address != null ? sessionScope.user.address : ''}" 
                       required placeholder="Ví dụ: Số 1, Đường ABC, Quận XYZ">

                <label for="phone">Số điện thoại:</label>
                <input type="text" id="phone" name="phone" 
                       value="${sessionScope.user.phone != null ? sessionScope.user.phone : ''}" 
                       required placeholder="Ví dụ: 0912345678">

                <div class="buttons">
                    <button type="submit" name="action" value="update">Cập nhật thông tin</button>
                    <button type="submit" name="action" value="confirm">Hoàn tất đặt hàng</button>
                </div>
            </form>

            <div class="info">
                <p><strong>Tên khách hàng:</strong> ${sessionScope.user.name}</p>
                <p><strong>Email:</strong> ${sessionScope.user.email}</p>
                <p><strong>Thời gian đặt hàng:</strong> <fmt:formatDate value="<%= new java.util.Date()%>" pattern="HH:mm dd/MM/yyyy" /></p>
            </div>
            <c:if test="${not empty sessionScope.cart}">
                <h3 style="margin-top: 30px; color: #27c6ee;">Chi tiết giỏ hàng</h3>
                <table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
                    <thead style="background-color: #f0f8ff;">
                        <tr>
                            <th style="padding: 10px; border: 1px solid #ddd;">Sản phẩm</th>
                            <th style="padding: 10px; border: 1px solid #ddd;">Số lượng</th>
                            <th style="padding: 10px; border: 1px solid #ddd;">Đơn giá</th>
                            <th style="padding: 10px; border: 1px solid #ddd;">Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="total" value="0" />
                        ...
                        <c:forEach var="item" items="${sessionScope.cart}">
                            <tr>
                                <td style="padding: 10px; border: 1px solid #ddd;">${item.book.title}</td>
                                <td style="padding: 10px; border: 1px solid #ddd;">${item.quantity}</td>
                                <td style="padding: 10px; border: 1px solid #ddd;"><fmt:formatNumber value="${item.book.price}" type="number" /> ₫</td>
                                <td style="padding: 10px; border: 1px solid #ddd;"><fmt:formatNumber value="${item.book.price * item.quantity}" type="number" /> ₫</td>
                            </tr>
                            <c:set var="total" value="${total + (item.book.price * item.quantity)}" />
                        

                    </c:forEach>
                    <tr style="font-weight: bold; background-color: #f9f9f9;">
                        <td colspan="3" style="padding: 10px; text-align: right; border: 1px solid #ddd;">Tổng cộng:</td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><fmt:formatNumber value="${total}" type="number" /> ₫</td>
                    </tr>
                </tbody>
            </table>
        </c:if>

    </div>
</body>
</html>