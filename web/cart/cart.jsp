<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Giỏ hàng của bạn</title>
        <style>
            <%-- CSS giữ nguyên như bạn gửi --%>
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
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
            }
            .cart-container {
                max-width: 1200px;
                margin: 20px auto;
                padding: 20px;
                background-color: white;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                border-radius: 5px;
            }
            .cart-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 15px;
                border-bottom: 1px solid #eee;
                margin-bottom: 20px;
            }
            .cart-title {
                font-size: 24px;
                font-weight: bold;
                color: #333;
            }
            .continue-shopping, .checkout-button {
                display: inline-block;
                margin-top: 20px;
                padding: 12px 30px;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                text-decoration: none;
                border-radius: 4px;
                font-weight: bold;
                border: none;
                cursor: pointer;
                font-size: 14px;
                transition: background 0.3s;
            }
            .cart-item {
                display: flex;
                padding: 15px 0;
                border-bottom: 1px solid #eee;
            }
            .item-image {
                width: 100px;
                height: 100px;
                object-fit: contain;
                background-color: #f9f9f9;
                padding: 5px;
                margin-right: 20px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .item-details {
                flex-grow: 1;
            }
            .item-title {
                font-weight: bold;
                margin-bottom: 5px;
            }
            .item-variation {
                color: #666;
                font-size: 14px;
                margin-bottom: 5px;
            }
            .item-price {
                font-weight: bold;
                color: #d70018;
                margin-bottom: 10px;
            }
            .item-quantity {
                display: flex;
                align-items: center;
            }
            .quantity-btn {
                width: 30px;
                height: 30px;
                border: 1px solid #ddd;
                background: #f5f5f5;
                cursor: pointer;
            }
            .quantity-input {
                width: 50px;
                height: 30px;
                text-align: center;
                border: 1px solid #ddd;
                margin: 0 5px;
            }
            .item-actions {
                display: flex;
                flex-direction: column;
                align-items: flex-end;
                justify-content: space-between;
            }
            .item-total {
                font-weight: bold;
                color: #d70018;
                font-size: 18px;
            }
            .remove-button {
                color: #666;
                text-decoration: none;
                font-size: 14px;
                background: none;
                border: none;
                cursor: pointer;
            }
            .cart-summary {
                margin-top: 30px;
                text-align: right;
            }
            .total-amount {
                font-size: 24px;
                color: #d70018;
                font-weight: bold;
            }
            .empty-cart {
                text-align: center;
                padding: 50px 0;
            }
            .empty-cart-message {
                font-size: 18px;
                color: #666;
                margin-bottom: 20px;
            }
            .header_logo_with_title {
                display: flex;
                align-items: center;
            }
        </style>
    </head>
    <body>
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
                    <span class="title-text">Giỏ hàng</span>
                </div>
            </div>
        </div>

        <div class="cart-container">
            <c:choose>
                <c:when test="${empty sessionScope.cart}">
                    <div class="empty-cart">
                        <p class="empty-cart-message">Giỏ hàng của bạn đang trống</p>
                        <a href="${pageContext.request.contextPath}/home" class="checkout-button">Tiếp tục mua sắm</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="cart-header">
                        <div class="cart-title">Giỏ hàng của bạn</div>
                        <a href="${pageContext.request.contextPath}/home" class="continue-shopping">Tiếp tục mua sắm</a>
                    </div>

                    <c:set var="total" value="0" />

                    <h3>Sách chưa thanh toán</h3>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:if test="${item.status == 'chưa thanh toán'}">
                            <div class="cart-item">
                                <img src="${item.book.imageURL}" alt="${item.book.title}" class="item-image">
                                <div class="item-details">
                                    <div class="item-title">${item.book.title}</div>
                                    <div class="item-variation">Phân loại hàng: ${item.book.categoryName}</div>
                                    <div class="item-variation"><strong>Trạng thái:</strong> <span style="color: green;">${item.status}</span></div>
                                    <div class="item-price"><fmt:formatNumber value="${item.book.price}" type="number" groupingUsed="true" /> ₫</div>
                                    <div class="item-quantity">
                                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                            <input type="hidden" name="action" value="decrease" />
                                            <input type="hidden" name="bookId" value="${item.book.id}" />
                                            <button type="submit" class="quantity-btn">-</button>
                                        </form>
                                        <input type="text" class="quantity-input" value="${item.quantity}" readonly />
                                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                            <input type="hidden" name="action" value="increase" />
                                            <input type="hidden" name="bookId" value="${item.book.id}" />
                                            <button type="submit" class="quantity-btn">+</button>
                                        </form>
                                    </div>
                                </div>
                                <div class="item-actions">
                                    <div class="item-total"><fmt:formatNumber value="${item.book.price * item.quantity}" type="number" groupingUsed="true" /> ₫</div>
                                    <a href="${pageContext.request.contextPath}/cart?action=remove&bookId=${item.book.id}" class="remove-button">Xóa</a>
                                </div>
                            </div>
                            <c:set var="total" value="${total + (item.book.price * item.quantity)}" />
                        </c:if>
                    </c:forEach>

                    <h3>Sách đang xử lý</h3>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:if test="${item.status == 'đang xử lý'}">
                            <div class="cart-item">
                                <img src="${item.book.imageURL}" class="item-image">
                                <div class="item-details">
                                    <div class="item-title">${item.book.title}</div>
                                    <div class="item-variation">Phân loại: ${item.book.categoryName}</div>
                                    <div class="item-price"><fmt:formatNumber value="${item.book.price}" type="number" groupingUsed="true" /> ₫</div>
                                    <div class="item-variation"><strong>Trạng thái:</strong> <span style="color: orange;">${item.status}</span></div>
                                </div>
                                <div class="item-actions">
                                    <div class="item-total"><fmt:formatNumber value="${item.book.price * item.quantity}" type="number" groupingUsed="true" /> ₫</div>
                                    <form action="${pageContext.request.contextPath}/cart" method="get">
                                        <input type="hidden" name="action" value="cancel" />
                                        <input type="hidden" name="bookId" value="${item.book.id}" />
                                        <button type="submit" class="remove-button">Hủy đơn hàng</button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>

                    <c:if test="${total > 0}">
                        <div class="cart-summary">
                            <div style="margin-bottom: 10px;">Tổng thanh toán:</div>
                            <div class="total-amount"><fmt:formatNumber value="${total}" type="number" groupingUsed="true" /> ₫</div>
                            <a href="${pageContext.request.contextPath}/confirm" class="checkout-button">Mua hàng</a>

                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
