<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cảm ơn bạn đã đặt hàng</title>
        <style>
            /* CSS như cũ */
            .header_top {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 30px;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                font-size: 14px;
            }
            .header_top a {
                color: white;
                text-decoration: none;
                margin-left: 15px;
            }
            .header_top a:hover {
                text-decoration: underline;
            }
            .header_login {
                font-weight: bold;
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
                font-family: 'Segoe UI', sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
            }
            .thankyou-container {
                max-width: 800px;
                margin: 40px auto;
                padding: 40px;
                background: white;
                border-radius: 8px;
                box-shadow: 0 0 15px rgba(0,0,0,0.05);
            }
            .thankyou-header {
                text-align: center;
                margin-bottom: 30px;
            }
            .thankyou-icon {
                font-size: 60px;
                color: #4CAF50;
                margin-bottom: 15px;
            }
            h2 {
                color: #27c6ee;
                margin-bottom: 10px;
            }
            .section-title {
                color: #27c6ee;
                border-bottom: 1px solid #eee;
                padding-bottom: 8px;
                margin-bottom: 15px;
                font-size: 20px;
            }
            .order-details {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
            }
            .info-box {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 6px;
                margin-bottom: 20px;
            }
            .info-row {
                margin-bottom: 10px;
                display: flex;
            }
            .info-label {
                font-weight: bold;
                min-width: 120px;
                color: #555;
            }
            .info-value {
                flex: 1;
            }
            .ordered-items {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }
            .ordered-items th {
                background: #f0f8ff;
                padding: 10px;
                text-align: left;
            }
            .ordered-items td {
                padding: 10px;
                border-bottom: 1px solid #eee;
            }
            .item-price {
                color: #d70018;
                font-weight: bold;
            }
            .total-row {
                font-weight: bold;
                background: #f9f9f9;
            }
            .button-group {
                text-align: center;
                margin-top: 40px;
            }
            .btn {
                display: inline-block;
                padding: 12px 30px;
                margin: 0 10px;
                border-radius: 4px;
                font-weight: bold;
                text-decoration: none;
                transition: all 0.3s;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
            }
            .btn:hover {
                background: linear-gradient(90deg, #1a7a8f, #5cc5e0);
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
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
                    <span class="title-text">Đặt hàng thành công</span>
                </div>
            </div>
        </div>

        <div class="thankyou-container">
            <div class="thankyou-header">
                <div class="thankyou-icon">✓</div>
                <h2>Cảm ơn bạn đã đặt hàng tại BookZone!</h2>
                <p>Đơn hàng của bạn đã được tiếp nhận và đang được xử lý.</p>
            </div>

            <h3 class="section-title">Thông tin khách hàng</h3>
            <div class="order-details">
                <div class="info-box" style="flex:1">
                    <div class="info-row">
                        <span class="info-label">Họ tên:</span>
                        <span class="info-value">${sessionScope.user.name}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Số điện thoại:</span>
                        <span class="info-value">${sessionScope.user.phone}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Địa chỉ:</span>
                        <span class="info-value">${sessionScope.user.address}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Email:</span>
                        <span class="info-value">${sessionScope.user.email}</span>
                    </div>
                </div>

                <div class="info-box" style="flex:1">
                    <div class="info-row">
                        <span class="info-label">Ngày đặt:</span>
                        <span class="info-value"><fmt:formatDate value="<%= new java.util.Date()%>" pattern="HH:mm dd/MM/yyyy" /></span>
                    </div>
                    <div class="info-row">
    <span class="info-label">Phương thức:</span>
    <span class="info-value">
        <c:choose>
            <c:when test="${sessionScope.lastPaymentMethod == 'COD'}">Thanh toán khi nhận hàng</c:when>
            <c:when test="${sessionScope.lastPaymentMethod == 'MOMO'}">Thanh toán qua ví Momo</c:when>
            <c:when test="${sessionScope.lastPaymentMethod == 'VNPAY'}">Thanh toán qua VNPAY</c:when>
            <c:otherwise>Không xác định</c:otherwise>
        </c:choose>
    </span>
</div>

                    <div class="info-row">
                        <span class="info-label">Tình trạng:</span>
                        <span class="info-value">${sessionScope.lastOrder.status}</span>
                    </div>
                </div>
            </div>

            <h3 class="section-title">Chi tiết đơn hàng</h3>
            <table class="ordered-items">
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                        <th>Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${sessionScope.lastCart}">
                        <tr>
                            <td>${item.book.title}</td>
                            <td>${item.quantity}</td>
                            <td class="item-price"><fmt:formatNumber value="${item.book.price}" type="number" /> ₫</td>
                            <td class="item-price"><fmt:formatNumber value="${item.book.price * item.quantity}" type="number" /> ₫</td>
                        </tr>
                    </c:forEach>
                    <tr class="total-row">
                        <td colspan="3" style="text-align: right;">Tổng cộng:</td>
                        <td class="item-price"><fmt:formatNumber value="${sessionScope.lastOrder.totalPrice}" type="number" /> ₫</td>
                    </tr>
                </tbody>
            </table>

            <div class="button-group">
                <a href="${pageContext.request.contextPath}/home" class="btn">Tiếp tục mua sắm</a>
                <a href="${pageContext.request.contextPath}/cartinformation" class="btn">Xem trạng thái đơn hàng</a>
            </div>

        </div>

        <%
            // Không xóa cart để giữ lại đơn hàng với trạng thái đang xử lý
            session.removeAttribute("orderCompleted");
            session.removeAttribute("lastOrder");
            session.removeAttribute("lastCart");
        %>
        <jsp:include page="/footer.jsp" />
    </body>
</html>

