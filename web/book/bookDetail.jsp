<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    session.setAttribute("lastBookId", request.getParameter("id"));
%>

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
                max-width: 250px;
                max-height: 350px;
                width: auto;
                height: auto;
                object-fit: cover;
                border-radius: 8px;
                border: 1px solid #eee;
                display: block;
                margin: 0 auto;
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

            /* Bình luận đã viết */
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

            /* Header với tên + sao bên trái, nút ⋮ bên phải */
            .comment-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 8px;
            }

            /* Gộp tên và sao cùng 1 dòng bên trái */
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

            /* Nút ⋮ ngoài cùng bên phải */
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

            /* Nội dung comment */
            .comment-content {
                font-size: 15px;
                color: #444;
                margin-bottom: 5px;
            }

            .comment-date {
                font-size: 13px;
                color: #888;
            }

            /* Form viết bình luận */
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

            /* Rating input (sao) khi viết bình luận mới */
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

            /* Form chỉnh sửa inline */
            .edit-form {
                margin-top: 10px;
                background: #fafafa;
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ddd;
            }

            /*Goi y sach */
            .related-books {
                display: flex;
                gap: 20px;
                margin-top: 20px;
                flex-wrap: wrap;
            }

            .related-book-card {
                width: 160px;
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 10px;
                text-align: center;
                background: #fff;
                box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                transition: 0.3s;
            }

            .related-book-card img {
                width: 100%;
                height: 220px;
                object-fit: cover;
                border-radius: 4px;
            }

            .related-book-card:hover {
                box-shadow: 0 4px 10px rgba(0,0,0,0.15);
                transform: translateY(-3px);
            }

            .related-title {
                font-weight: bold;
                color: #333;
                margin: 8px 0 4px;
            }

            .related-price {
                color: #e53935;
                font-weight: bold;
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


            /*css gợi ý sách*/
            .related-books {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                margin-top: 20px;
                justify-content: center;
            }

            .related-book-link {
                text-decoration: none;
                color: inherit;
            }

            .related-book-card {
                width: 180px;
                background: #ffffff;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
                text-align: center;
                transition: transform 0.3s;
                position: relative;
            }

            .related-book-card:hover {
                transform: translateY(-5px);
            }

            .related-image-wrapper {
                position: relative;
                width: 100%;
                height: 220px;
                overflow: hidden;
            }

            .related-image-wrapper img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                border-bottom: 1px solid #eee;
                background-color: #fff;
            }

            .book-discount {
                position: absolute;
                top: 8px;
                right: 8px;
                background-color: #e53935;
                color: white;
                font-size: 14px;
                font-weight: bold;
                padding: 6px 8px;
                border-radius: 50%;
            }

            .related-info {
                padding: 10px;
            }

            .related-title {
                font-size: 14px;
                font-weight: 500;
                margin: 6px 0;
                height: 40px;
                overflow: hidden;
            }

            .related-prices {
                margin-top: 5px;
            }

            .related-price-sale {
                color: #e53935;
                font-weight: bold;
                margin-right: 5px;
            }

            .related-price-original {
                color: #777;
                text-decoration: line-through;
                font-size: 13px;
            }

            .related-more-link {
                margin-top: 15px;
                text-align: right;
            }

            .related-more-link a {
                display: inline-block;
                padding: 6px 12px;
                background-color: #f5f5f5;
                color: #d32f2f;
                text-decoration: none;
                border-radius: 4px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.08);
                font-size: 14px;
                transition: background-color 0.3s ease;
            }

            .related-more-link a:hover {
                background-color: #e0e0e0;
                text-decoration: underline;
            }

            .section-title-bar {
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 40px 0 20px;
                position: relative;
            }

            .section-title-bar::before,
            .section-title-bar::after {
                content: "";
                flex: 1;
                height: 1px;
                background-color: #ccc;
                margin: 0 15px;
            }

            .section-title {
                font-size: 28px;
                font-weight: bold;
                color: #2c3e50;
                text-transform: uppercase;
                letter-spacing: 1px;
                text-align: center;
                margin: 0;
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
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="bookId" value="${book.id}" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="buy-now">Mua ngay</button>
                        </form>
                    </div>
                </div>
            </div>


            <!-- Comment  -->
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
                                                <input type="hidden" name="bookId" value="${book.id}"/>
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
                                    <input type="hidden" name="bookId" value="${book.id}"/>

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
                <p>Chưa có bình luận nào cho sách này.</p>
            </c:if>
            <!-- PAGINATION for COMMENTS -->
            <c:if test="${commentTotalPages > 1}">
                <div class="comment-pagination">
                    <!-- Nút prev -->
                    <c:if test="${commentPage > 1}">
                        <a href="${pageContext.request.contextPath}/bookdetail?id=${book.id}&commentPage=${commentPage - 1}">&laquo;</a>
                    </c:if>

                    <!-- Trang số quanh current -->
                    <c:set var="startPage" value="${commentPage - 2 < 1 ? 1 : commentPage - 2}" />
                    <c:set var="endPage" value="${commentPage + 2 > commentTotalPages ? commentTotalPages : commentPage + 2}" />

                    <c:forEach var="p" begin="${startPage}" end="${endPage}">
                        <c:choose>
                            <c:when test="${p == commentPage}">
                                <strong>${p}</strong>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/bookdetail?id=${book.id}&commentPage=${p}">${p}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- Nút next -->
                    <c:if test="${commentPage < commentTotalPages}">
                        <a href="${pageContext.request.contextPath}/bookdetail?id=${book.id}&commentPage=${commentPage + 1}">&raquo;</a>
                    </c:if>
                </div>
            </c:if>




            <div class="comment-form">
                <h3>Viết bình luận</h3>
                <form action="${pageContext.request.contextPath}/comment" method="post">
                    <input type="hidden" name="action" value="add" />
                    <input type="hidden" name="bookId" value="${book.id}" />

                    <div class="rating-row">
                        <span class="rating-label">Đánh giá:</span>
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

            <!--Goi y sach -->
            <c:if test="${not empty relatedBooks}">
                <div class="section-title-bar">
                    <h2 class="section-title">Gợi ý sách cùng loại</h2>
                </div>

                <div class="related-books">
                    <c:forEach var="item" items="${relatedBooks}">
                        <a href="${pageContext.request.contextPath}/bookdetail?id=${item.id}" class="related-book-link">
                            <div class="related-book-card">
                                <div class="related-image-wrapper">
                                    <img src="${item.imageURL}" alt="${item.title}" />
                                    <c:if test="${item.discount > 0}">
                                        <div class="book-discount">-${item.discount}%</div>
                                    </c:if>
                                </div>
                                <div class="related-info">
                                    <div class="related-title">${item.title}</div>
                                    <c:set var="finalPrice" value="${item.price * (1 - (item.discount / 100.0))}" />
                                    <div class="related-prices">
                                        <c:choose>
                                            <c:when test="${item.discount > 0}">
                                                <span class="related-price-sale">
                                                    <fmt:formatNumber value="${finalPrice}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                                </span>
                                                <span class="related-price-original">
                                                    <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="related-price-sale">
                                                    <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
                <div class="related-more-link">
                    <a href="${pageContext.request.contextPath}/categorybooks?categoryId=${book.categoryID}">Xem thêm >></a>
                </div>
            </c:if>



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



        //hihi
    </body>
</html>