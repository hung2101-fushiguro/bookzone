<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>BookZone - Chi Tiết Sách</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background: #f5f5f5;
            }
            .header_top, .header_main {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 30px;
            }
            .header_top {
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                font-size: 14px;
            }
            .header_main {
                background: white;
                border-bottom: 2px solid #e0e0e0;
                color: black;
            }
            .header_logo img {
                height: 80px;
            }
            .header_search-box {
                flex: 1;
                display: flex;
                justify-content: center;
                margin: 0 40px;
            }
            .search-input {
                width: 400px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px 0 0 4px;
            }
            .search-button {
                padding: 8px 15px;
                border: none;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                border-radius: 0 4px 4px 0;
                cursor: pointer;
            }
            .header_utilities {
                display: flex;
                gap: 20px;
                align-items: center;
                font-size: 14px;
            }
            .header_utilities a {
                color: black;
                text-decoration: none;
            }
            .container {
                max-width: 1200px;
                margin: 30px auto;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                padding: 20px;
            }
            .book-detail-wrapper {
                display: flex;
                flex-wrap: wrap;
                gap: 30px;
            }
            .book-images {
                flex: 0 0 35%;
                display: flex;
                justify-content: center;
            }
            .book-images img {
                width: 100%;
                max-width: 250px;
                border-radius: 8px;
                border: 1px solid #eee;
            }
            .book-info {
                flex: 0 0 60%;
            }
            .book-info h1 {
                font-size: 28px;
                margin-bottom: 10px;
                color: #333;
            }
            .book-prices {
                display: flex;
                align-items: baseline;
                gap: 15px;
                margin-bottom: 10px;
            }
            .price-sale {
                font-size: 26px;
                font-weight: bold;
                color: #e53935;
            }
            .price-original {
                font-size: 20px;
                color: #888;
                text-decoration: line-through;
            }
            .discount-badge {
                background-color: #e53935;
                color: white;
                font-size: 14px;
                padding: 3px 8px;
                border-radius: 4px;
            }
            .book-attributes p {
                margin: 6px 0;
                font-size: 15px;
            }
            .book-attributes strong {
                display: inline-block;
                width: 120px;
            }
            .book-description {
                margin-top: 10px;
                font-size: 15px;
                color: #444;
                line-height: 1.6;
            }
            .book-buy-actions {
                display: flex;
                gap: 15px;
                margin-top: 15px;
            }
            .book-buy-actions form {
                flex: 1;
            }
            .book-buy-actions button {
                width: 100%;
                padding: 12px;
                font-size: 16px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: 0.3s;
            }
            .add-cart {
                background-color: #ff9800;
                color: white;
            }
            .buy-now {
                background-color: #e53935;
                color: white;
            }
            .book-buy-actions button:hover {
                opacity: 0.9;
            }

            @media (max-width: 768px) {
                .book-detail-wrapper {
                    flex-direction: column;
                }
                .book-images, .book-info {
                    max-width: 100%;
                }
            }
        </style>
    </head>
    <body>

        <!-- HEADER -->
        <div class="header_top">
            <div>Hotline: <strong>0938 424 289</strong></div>
            <div>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span>${sessionScope.user.name}</span>
                        <a href="${pageContext.request.contextPath}/logout">| Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                        <a href="${pageContext.request.contextPath}/register">| Đăng ký</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="header_main">
            <a class="header_logo" href="${pageContext.request.contextPath}/home">
                <img src="${pageContext.request.contextPath}/image/logo1.jpg" alt="BookZone Logo">
            </a>
            <form class="header_search-box" action="${pageContext.request.contextPath}/search" method="get">
                <input type="text" class="search-input" name="query" placeholder="Tìm kiếm sách...">
                <button type="submit" class="search-button">Tìm</button>
            </form>
            <div class="header_utilities">
                <c:set var="cartCount" value="0" />
                <c:if test="${not empty sessionScope.cart}">
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:set var="cartCount" value="${cartCount + item.quantity}" />
                    </c:forEach>
                </c:if>

                <a href="${pageContext.request.contextPath}/offers"><strong>Ưu đãi&Tiện ích</strong></a>
                <a href="${pageContext.request.contextPath}/cart"><strong>🛒 Giỏ hàng</strong> (${cartCount})</a>
            </div>
        </div>

        <!-- BOOK DETAIL -->
        <div class="container">
            <div class="book-detail-wrapper">
                <div class="book-images">
                    <img src="${book.imageURL}" alt="${book.title}">
                </div>
                <div class="book-info">
                    <h1>${book.title}</h1>

                    <c:set var="discountValue" value="${empty book.discount ? 0 : book.discount}" />
                    <c:set var="finalPrice" value="${book.price * (1 - discountValue / 100.0)}" />

                    <div class="book-prices">
                        <c:choose>
                            <c:when test="${discountValue > 0}">
                                <span class="price-sale">
                                    <fmt:formatNumber value="${finalPrice}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                </span>
                                <span class="price-original">
                                    <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                </span>
                                <span class="discount-badge">-${discountValue}%</span>
                            </c:when>
                            <c:otherwise>
                                <span class="price-sale">
                                    <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="book-attributes">
                        <p><strong>Mã sách:</strong> ${book.id}</p>
                        <p><strong>Tác giả:</strong> ${book.author}</p>
                        <p><strong>Ngày thêm:</strong> <fmt:formatDate value="${book.created_at}" pattern="dd/MM/yyyy"/></p>
                        <p><strong>Số lượng còn:</strong> ${book.quantity}</p>
                    </div>

                    <div class="book-description">
                        <strong>Mô tả:</strong> ${book.description}
                    </div>

                    <div class="book-buy-actions">
                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display: flex; gap: 10px; align-items: center;">
                            <input type="hidden" name="bookId" value="${book.id}" />

                            <label>
                                <input type="number" name="quantity" value="1" min="1"
                                       style="width: 60px; padding: 6px; border: 1px solid #ccc; border-radius: 4px;">
                            </label>

                            <button type="submit" class="add-cart">🛒 Thêm vào giỏ hàng</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/checkout" method="post">
                            <input type="hidden" name="bookId" value="${book.id}" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="buy-now">Mua ngay</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
