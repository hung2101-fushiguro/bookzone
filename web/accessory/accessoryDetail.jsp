<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>BookZone - Chi tiết Phụ kiện</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background: #f5f5f5;
            }

            /* HEADER giữ y như HOME */
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
            .search-button:hover {
                background: linear-gradient(90deg, #1976d2, #64b5f6);
            }
            .header_utilities {
                display: flex;
                gap: 20px;
                align-items: center;
                font-size: 14px;
            }
            .utility_link {
                color: #333;
                text-decoration: none;
            }
            .utility_link:hover {
                color: #00a651;
            }

            /* DETAIL CONTAINER */
            .container {
                max-width: 1000px;
                margin: 30px auto;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                padding: 20px;
            }
            .detail-wrapper {
                display: flex;
                flex-wrap: wrap;
                gap: 30px;
            }
            .detail-images {
                flex: 0 0 40%;
                display: flex;
                justify-content: center;
            }
            .detail-images img {
                max-width: 300px;
                max-height: 400px;
                width: auto;
                height: auto;
                object-fit: cover;
                border-radius: 8px;
                border: 1px solid #eee;
                display: block;
                margin: 0 auto;
            }
            .detail-info {
                flex: 0 0 55%;
            }
            .detail-info h1 {
                font-size: 26px;
                margin-bottom: 15px;
                color: #333;
            }
            .detail-price {
                font-size: 24px;
                font-weight: bold;
                color: #e53935;
                margin-bottom: 15px;
            }
            .detail-attributes p {
                margin: 6px 0;
                font-size: 15px;
                color: #444;
            }
            .detail-description {
                margin-top: 10px;
                font-size: 15px;
                color: #444;
                line-height: 1.6;
            }
            .buy-actions {
                display: flex;
                gap: 15px;
                margin-top: 20px;
            }
            .buy-actions button {
                flex: 1;
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
            .buy-actions button:hover {
                opacity: 0.9;
            }
            .back-link {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                background-color: #2193b0;
                color: white;
                padding: 8px 16px;
                border-radius: 6px;
                transition: background-color 0.3s;
            }
            .back-link:hover {
                background-color: #1976d2;
            }

            /* COMMENTS */
            .comment-section {
                margin-top: 40px;
            }
            .comment-card {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 15px 20px;
                margin-bottom: 15px;
                background-color: #fff;
                box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                transition: 0.3s;
            }
            .comment-card:hover {
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }
            .comment-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 8px;
            }
            .comment-user-rating {
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .comment-user {
                font-weight: bold;
                color: #2193b0;
            }
            .comment-rating {
                color: #e53935;
                font-weight: bold;
            }
            .comment-content {
                font-size: 15px;
                color: #444;
                margin-bottom: 5px;
            }
            .comment-date {
                font-size: 13px;
                color: #888;
            }
            .comment-form {
                margin-top: 30px;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            }
            .comment-form h3 {
                margin-top: 0;
                color: #333;
                margin-bottom: 15px;
            }
            .comment-form textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                margin-top: 5px;
                margin-bottom: 15px;
                font-size: 14px;
            }
            .comment-form button {
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                border: none;
                padding: 10px 20px;
                font-size: 15px;
                border-radius: 6px;
                cursor: pointer;
                transition: background 0.3s;
            }
            .comment-form button:hover {
                background: linear-gradient(90deg, #1976a5, #5cc2e2);
            }
            .rating-row {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-bottom: 10px;
            }
            .rating {
                display: flex;
                flex-direction: row-reverse;
                justify-content: flex-start;
            }
            .rating input {
                display: none;
            }
            .rating label {
                font-size: 30px;
                color: #ccc;
                cursor: pointer;
                transition: color 0.2s;
            }
            .rating input:checked ~ label,
            .rating label:hover,
            .rating label:hover ~ label {
                color: #ffc107;
            }

            @media (max-width: 768px) {
                .detail-wrapper {
                    flex-direction: column;
                }
                .detail-images, .detail-info {
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
                <a href="${pageContext.request.contextPath}/offers" class="utility_link"><strong>Ưu đãi & Tiện ích</strong></a>
                <a href="${pageContext.request.contextPath}/cart" class="utility_link"><strong>🛒 Giỏ hàng (${cartCount})</strong></a>
            </div>
        </div>

        <!-- DETAIL CONTENT -->
        <div class="container">
            <div class="detail-wrapper">
                <div class="detail-images">
                    <img src="${accessory.imageUrl}" alt="${accessory.name}" />
                </div>
                <div class="detail-info">
                    <h1>${accessory.name}</h1>
                    <div class="detail-price">
                        <fmt:formatNumber value="${accessory.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                    </div>
                    <div class="detail-attributes">
                        <p><strong>Phân loại:</strong> ${accessory.category.name}</p>
                        <p><strong>Ngày thêm:</strong> ${accessory.createdAt}</p>
                    </div>
                    <div class="detail-description">
                        <strong>Mô tả:</strong> ${accessory.description}
                    </div>
                    <div class="buy-actions">
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="accessoryId" value="${accessory.id}" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="add-cart">🛒 Thêm vào giỏ hàng</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/checkout" method="post">
                            <input type="hidden" name="accessoryId" value="${accessory.id}" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="buy-now">Mua ngay</button>
                        </form>
                    </div>
                    <a href="accessorylist" class="back-link">← Quay lại danh sách</a>
                </div>
            </div>

            <!-- COMMENT SECTION -->
            <h2 class="comment-section">Bình luận</h2>
            <c:if test="${not empty comments}">
                <div>
                    <c:forEach var="c" items="${comments}">
                        <div class="comment-card">
                            <div class="comment-header">
                                <div class="comment-user-rating">
                                    <span class="comment-user">${c.userName}</span>
                                    <span class="comment-rating">${c.rating} ⭐</span>
                                </div>
                            </div>
                            <div class="comment-content">${c.content}</div>
                            <div class="comment-date">${c.createdAt}</div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${empty comments}">
                <p>Chưa có bình luận nào cho phụ kiện này.</p>
            </c:if>

            <div class="comment-form">
                <h3>Viết bình luận</h3>
                <form action="${pageContext.request.contextPath}/comment" method="post">
                    <input type="hidden" name="action" value="add" />
                    <input type="hidden" name="accessoryId" value="${accessory.id}" />

                    <div class="rating-row">
                        <span>Đánh giá:</span>
                        <div class="rating">
                            <input type="radio" id="star5" name="rating" value="5" required><label for="star5">★</label>
                            <input type="radio" id="star4" name="rating" value="4"><label for="star4">★</label>
                            <input type="radio" id="star3" name="rating" value="3"><label for="star3">★</label>
                            <input type="radio" id="star2" name="rating" value="2"><label for="star2">★</label>
                            <input type="radio" id="star1" name="rating" value="1"><label for="star1">★</label>
                        </div>
                    </div>

                    <label>Nội dung:</label>
                    <textarea name="content" rows="4" placeholder="Viết bình luận của bạn..." required></textarea>

                    <button type="submit">Gửi bình luận</button>
                </form>
            </div>
        </div>

    </body>
</html>
