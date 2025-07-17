<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%
    session.setAttribute("lastAccessoryId", request.getParameter("id"));
%>

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

            .comment-actions {
                position: relative;
            }

            .menu-toggle {
                background: none;
                border: none;
                cursor: pointer;
                font-size: 18px;
            }

            .menu-options {
                position: absolute;
                right: 0;
                background: #fff;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                padding: 5px;
                z-index: 100;
            }

            .menu-options button {
                background: none;
                border: none;
                cursor: pointer;
                display: block;
                padding: 5px 10px;
                width: 100%;
                text-align: left;
            }

            .menu-options button:hover {
                background-color: #f0f0f0;
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

            .comment-form label {
                font-weight: 500;
                margin-right: 10px;
            }

            .comment-form input[type="number"],
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

            .rating-label {
                font-weight: bold;
                font-size: 16px;
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

            .edit-form {
                margin-top: 10px;
                background: #fafafa;
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ddd;
            }

            .comment-pagination {
                text-align: center;
                margin-top: 20px;
            }

            .comment-pagination a,
            .comment-pagination strong {
                display: inline-block;
                margin: 0 3px;
                padding: 6px 10px;
                border-radius: 4px;
                text-decoration: none;
                font-size: 14px;
                color: #000;
                border: 1px solid #ccc;
                background-color: transparent;
                transition: background-color 0.2s;
            }

            .comment-pagination a:hover {
                background-color: #f0f0f0;
            }

            .comment-pagination strong {
                background-color: #ddd;
                font-weight: bold;
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
                                <c:if test="${sessionScope.user != null && sessionScope.user.id == c.userId}">
                                    <div class="comment-actions">
                                        <button class="menu-toggle" onclick="toggleMenu('${c.commentId}')">⋮</button>
                                        <div class="menu-options" id="menu-${c.commentId}" style="display: none;">
                                            <button type="button" onclick="showEditForm('${c.commentId}')">Sửa</button>
                                            <form action="${pageContext.request.contextPath}/comment" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="delete"/>
                                                <input type="hidden" name="commentId" value="${c.commentId}"/>
                                                <input type="hidden" name="accessoryId" value="${accessory.id}"/>
                                                <button type="submit">Xóa</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                            <div class="comment-content" id="content-${c.commentId}">
                                ${c.content}
                            </div>
                            <div class="comment-date">
                                <fmt:formatDate value="${c.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                            </div>

                            <!-- FORM EDIT -->
                            <div class="edit-form" id="edit-form-${c.commentId}" style="display:none;">
                                <form action="${pageContext.request.contextPath}/comment" method="post">
                                    <input type="hidden" name="action" value="update"/>
                                    <input type="hidden" name="commentId" value="${c.commentId}"/>
                                    <input type="hidden" name="accessoryId" value="${accessory.id}"/>

                                    <label>Đánh giá:</label>
                                    <input type="number" name="rating" value="${c.rating}" min="1" max="5" required>
                                    <label>Nội dung:</label>
                                    <input type="text" name="content" value="${c.content}" required>
                                    <button type="submit">Lưu</button>
                                    <button type="button" onclick="cancelEdit('${c.commentId}')">Huỷ</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${empty comments}">
                <p>Chưa có bình luận nào cho phụ kiện này.</p>
            </c:if>

            <!-- PAGINATION -->
            <c:if test="${commentTotalPages > 1}">
                <div class="comment-pagination">
                    <c:if test="${commentPage > 1}">
                        <a href="${pageContext.request.contextPath}/accessorydetail?id=${accessory.id}&commentPage=${commentPage - 1}">&laquo;</a>
                    </c:if>

                    <c:set var="startPage" value="${commentPage - 2 < 1 ? 1 : commentPage - 2}" />
                    <c:set var="endPage" value="${commentPage + 2 > commentTotalPages ? commentTotalPages : commentPage + 2}" />

                    <c:forEach var="p" begin="${startPage}" end="${endPage}">
                        <c:choose>
                            <c:when test="${p == commentPage}">
                                <strong>${p}</strong>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/accessorydetail?id=${accessory.id}&commentPage=${p}">${p}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${commentPage < commentTotalPages}">
                        <a href="${pageContext.request.contextPath}/accessorydetail?id=${accessory.id}&commentPage=${commentPage + 1}">&raquo;</a>
                    </c:if>
                </div>
            </c:if>

            <!-- COMMENT FORM -->
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

            <script>
                function toggleMenu(commentId) {
                    const menu = document.getElementById('menu-' + commentId);
                    menu.style.display = (menu.style.display === 'none') ? 'block' : 'none';
                }
                function showEditForm(commentId) {
                    document.getElementById('content-' + commentId).style.display = 'none';
                    document.getElementById('edit-form-' + commentId).style.display = 'block';
                    document.getElementById('menu-' + commentId).style.display = 'none';
                }
                function cancelEdit(commentId) {
                    document.getElementById('edit-form-' + commentId).style.display = 'none';
                    document.getElementById('content-' + commentId).style.display = 'block';
                }
            </script>



        </div>

    </body>
</html>