<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>BookZone - Trang Chủ</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">

        <!-- Slick Carousel -->
        <link rel="stylesheet" type="text/css"
              href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
        <link rel="stylesheet" type="text/css"
              href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script
        src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

        <style>
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f5f5f5;
            }

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
                border: 1px solid #00a651;
                border-left: none;
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
                display: flex;
                align-items: center;
            }

            .utility_link:hover {
                color: #00a651;
            }

            .icon-img {
                width: 20px;
                height: 20px;
                margin-right: 5px;
                object-fit: contain;
            }

            .container {
                display: flex;
                max-width: 1200px;
                width: 100%;
                margin: 20px auto;
            }

            .sidebar_menu {
                width: 230px;
                background-color: white;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            .sidebar_menu h3 {
                padding: 15px;
                margin: 0;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                font-size: 16px;
            }

            .sidebar_menu ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }

            .sidebar_menu ul li {
                border-bottom: 1px solid #eee;
            }

            .sidebar_menu ul li a {
                display: block;
                padding: 12px 15px;
                color: #333;
                text-decoration: none;
                font-weight: 500;
            }

            .sidebar_menu ul li a:hover {
                background-color: #f5f5f5;
                color: #00a651;
            }

            .main_content {
                flex: 1;
                margin-left: 20px;
                background-color: white;
                border-radius: 4px;
                padding: 10px;
                max-width: 940px;
                margin: 0 auto;
            }

            .slider-container-alt {
                position: relative;
                width: 100%;
                height: 400px;
                overflow: hidden;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            }

            .slider-container-alt .slide {
                position: absolute;
                width: 100%;
                height: 100%;
                opacity: 0;
                transition: opacity 1s ease-in-out;
            }

            .slider-container-alt .slide.active {
                opacity: 1;
            }

            .slider-container-alt img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                border-radius: 8px;
            }

            .slider-btn-alt {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background-color: rgba(0, 0, 0, 0.5);
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                z-index: 1;
                font-size: 24px;
                border-radius: 4px;
            }

            .slider-btn-alt:hover {
                background-color: rgba(0, 0, 0, 0.8);
            }

            .slider-btn-alt.prev {
                left: 15px;
            }

            .slider-btn-alt.next {
                right: 15px;
            }

            .book-link {
                text-decoration: none;
                color: inherit;
            }

            .book-item {
                flex: 0 0 auto;
                position: relative;
                width: 180px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                padding: 10px;
                text-align: center;
                transition: transform 0.3s;
            }

            .book-item:hover {
                transform: translateY(-5px);
            }

            .book-item img {
                width: 100%;
                height: 240px;
                object-fit: cover;
                border-radius: 4px;
            }

            .book-title {
                font-size: 14px;
                font-weight: 500;
                margin-top: 8px;
                height: 40px;
                overflow: hidden;
            }

            .book-price {
                margin-top: 5px;
            }

            .price-sale {
                color: red;
                font-weight: bold;
                margin-right: 5px;
            }

            .price-original {
                color: #777;
                text-decoration: line-through;
                font-size: 13px;
            }

            .book-discount {
                position: absolute;
                top: 8px;
                right: 8px;
                background-color: red;
                color: white;
                font-size: 16px;           /* TĂNG size chữ */
                font-weight: bold;
                padding: 6px 8px;          /* TĂNG độ rộng/gọn badge */
                border-radius: 50%;
            }


            /* Tùy chỉnh mũi tên slick */
            .slick-prev:before,
            .slick-next:before {
                font-size: 30px;
                color: black;
            }

            /* Phần hiển thị sách theo danh mục */
            .category-section {
                background-color: #fff;
                padding: 40px 20px;
                margin-bottom: 60px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                position: relative;
            }

            .category-banner {
                width: 100%;
                height: 180px;
                border-radius: 8px;
                overflow: hidden;
                margin-bottom: 20px;
            }

            .category-banner img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                display: block;
            }

            .category-banner a:hover img {
                opacity: 0.9;
                transition: opacity 0.3s ease;
            }


            .category-title {
                font-size: 32px;
                font-weight: bold;
                text-align: center;
                margin: 10px 0 30px;
                color: #2c3e50;
                text-transform: uppercase;
                letter-spacing: 1px;
                text-shadow: 1px 1px 1px rgba(0,0,0,0.1);
            }


            .book-row {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                justify-content: center;
            }

            .book-card {
                width: 180px;
                background: #ffffff;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
                text-align: center;
                transition: transform 0.3s;
            }

            .book-card:hover {
                transform: translateY(-5px);
            }

            .book-card img {
                width: 100%;
                height: 220px;
                object-fit: cover;
                border-bottom: 1px solid #eee;
            }

            .book-card p {
                margin: 10px 0 5px;
                font-size: 14px;
                padding: 0 8px;
            }

            .book-card p:last-child {
                font-weight: bold;
                color: #e74c3c;
                margin-bottom: 12px;
            }

            .view-more {
                display: block;
                text-align: right;
                margin-top: 20px;
                margin-right: 10px;
                font-weight: 500;
                color: #d32f2f;
                text-decoration: none;
                font-size: 14px;
                padding: 6px 12px;
                background-color: #f5f5f5;
                border-radius: 4px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.08);
                width: fit-content;
                float: right; /* đẩy sang phải */
                transition: background-color 0.3s ease;
            }

            .view-more:hover {
                background-color: #e0e0e0;
                text-decoration: underline;
            }


            .category-title-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 25px;
                padding: 0 10px;
            }

            .category-title-bar .category-title {
                margin: 0;
                font-size: 24px;
                font-weight: bold;
                color: #2c3e50;
                text-transform: uppercase;
                letter-spacing: 1px;
                text-shadow: 1px 1px 1px rgba(0,0,0,0.1);
            }

            .category-title-bar .view-more {
                font-size: 14px;
                color: #d32f2f;
                font-weight: 500;
                text-decoration: none;
                background-color: #f5f5f5;
                padding: 6px 12px;
                border-radius: 4px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.08);
                transition: background-color 0.3s ease;
            }

            .category-title-bar .view-more:hover {
                background-color: #e0e0e0;
                text-decoration: underline;
            }
            .category-header {
                display: flex;
                flex-direction: column;
                align-items: center;
                position: relative;
                margin-bottom: 20px;
            }

            .category-header .category-title {
                font-size: 32px;
                font-weight: bold;
                text-align: center;
                margin: 10px 0;
                color: #2c3e50;
                text-transform: uppercase;
                letter-spacing: 1px;
                text-shadow: 1px 1px 1px rgba(0,0,0,0.1);
            }

            .category-header .view-more {
                position: absolute;
                right: 10px;
                bottom: 0;
                font-size: 14px;
                color: #d32f2f;
                font-weight: 500;
                text-decoration: none;
                background-color: #f5f5f5;
                padding: 6px 12px;
                border-radius: 4px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.08);
                transition: background-color 0.3s ease;
            }
            .category-header .view-more:hover {
                background-color: #e0e0e0;
                text-decoration: underline;
            }/* Đảm bảo các ô sách đều nhau */
            .book-item, .book-card {
                height: 360px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            .book-item img, .book-card img {
                height: 220px;
                object-fit: cover;
            }

            .book-title, .book-card p {
                height: 40px;
                overflow: hidden;
            }
            .book-image-wrapper {
                width: 100%;
                height: 220px;
                overflow: hidden;
                border-radius: 4px;
            }

            .book-image-wrapper img {
                width: 100%;
                height: 100%;
                object-fit: contain;
                background-color: #fff; /* hoặc #f5f5f5 để đồng bộ nền */
            }
            .backToTop {
                display: none;
                position: fixed;
                bottom: 40px;
                right: 30px;
                z-index: 999;
                border: none;
                outline: none;
                background-color: #2196f3;
                color: white;
                cursor: pointer;
                padding: 12px 16px;
                border-radius: 50%;
                font-size: 18px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.2);
                transition: opacity 0.3s ease;
            }
            .backToTop:hover {
                background-color: #0b7dda;
            }
            .ai-float-button {
                position: fixed;
                bottom: 80px; /* nằm phía trên nút "Back to top" */
                right: 30px;
                background-color: #00c853;
                color: white;
                font-size: 24px;
                padding: 12px 14px;
                border-radius: 50%;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                text-decoration: none;
                z-index: 1000;
                transition: background-color 0.3s ease;
            }

            .ai-float-button:hover {
                background-color: #009624;
            }
            .ai-chat-button {
                position: fixed;
                bottom: 30px;
                right: 30px;
                background: #4a90e2;
                color: white;
                padding: 12px;
                border-radius: 50%;
                font-size: 20px;
                cursor: pointer;
                box-shadow: 0 0 10px rgba(0,0,0,0.2);
            }

            .ai-chat-box {
                display: none;
                position: fixed;
                bottom: 90px;
                right: 30px;
                width: 320px;
                background: white;
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.2);
                flex-direction: column;
                overflow: hidden;
                z-index: 9999;
            }

            .chat-header {
                background: #4a90e2;
                color: white;
                padding: 10px;
                font-weight: bold;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .chat-body {
                height: 300px;
                overflow-y: auto;
                padding: 10px;
                background: #f9f9f9;
            }

            .chat-input-area {
                display: flex;
                padding: 10px;
                border-top: 1px solid #eee;
                background: #fff;
            }

            .chat-input-area input {
                flex: 1;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                margin-right: 5px;
            }

            .chat-input-area button {
                background: #4a90e2;
                color: white;
                border: none;
                padding: 8px 12px;
                border-radius: 4px;
                cursor: pointer;
            }

            .chat-message {
                margin: 5px 0;
                padding: 8px 12px;
                border-radius: 15px;
                max-width: 80%;
                line-height: 1.4;
                word-wrap: break-word;
            }

            .user-message {
                background-color: #d1ecf1;
                align-self: flex-end;
                margin-left: auto;
            }

            .ai-message {
                background-color: #e2e3e5;
                align-self: flex-start;
                margin-right: auto;
                white-space: pre-wrap;
            }


        </style>
    </head>

    <body>
        <%-- HEADER & NAV --%>
        <%-- HEADER --%>
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
            <a class="header_logo" href="${pageContext.request.contextPath}/home">
                <img src="${pageContext.request.contextPath}/image/logo1.jpg" alt="Logo BookZone">
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
                <a href="${pageContext.request.contextPath}/offers" class="utility_link">
                    <strong>Ưu đãi & Tiện ích</strong>
                </a>
                <a href="${pageContext.request.contextPath}/cartinformation" class="utility_link">
                    <span class="icon">📦</span> <strong>Đơn hàng</strong>
                </a>

                <a href="${pageContext.request.contextPath}/cart" class="utility_link">
                    <span class="icon">🛒</span> <strong>Giỏ hàng (${cartCount})</strong>
                </a>
            </div>
        </div>

        <div class="container">
            <div class="sidebar_menu">
                <h3>Danh Mục Sách</h3>
                <ul>
                    <li><a href="categorybooks?categoryId=1">Tiểu thuyết ngôn tình</a></li>
                    <li><a href="categorybooks?categoryId=2">Sách khoa học</a></li>
                    <li><a href="categorybooks?categoryId=3">Sách lịch sử</a></li>
                    <li><a href="categorybooks?categoryId=4">Sách tâm lý</a></li>
                    <li><a href="categorybooks?categoryId=5">Sách thiếu nhi</a></li>
                    <li><a href="categorybooks?categoryId=6">Tiểu thuyết trinh thám</a></li>
                    <li><a href="categorybooks?categoryId=7">Sách kinh doanh</a></li>
                    <li><a href="categorybooks?categoryId=8">Sách kĩ năng sống</a></li>
                    <li><a href="categorybooks?categoryId=9">Văn học nước ngoài</a></li>
                    <li><a href="categorybooks?categoryId=10">Manga</a></li>
                </ul>
            </div>

            <div class="main_content">
                <%-- BANNER SLIDER --%>
                <div class="slider-container-alt">
                    <div class="slide active"><img src="image/banner1_3.jpg" alt="Banner 1"></div>
                    <div class="slide"><img src="image/banner2_1.jpg" alt="Banner 2"></div>
                    <div class="slide"><img src="image/banner3_1.jpg" alt="Banner 3"></div>
                    <div class="slide"><img src="image/banner4.jpg" alt="Banner 4"></div>
                    <button class="slider-btn-alt prev" onclick="changeSlide(-1)">&#10094;</button>
                    <button class="slider-btn-alt next" onclick="changeSlide(1)">&#10095;</button>
                </div>

                <%-- SÁCH MỚI SLIDER --%>
                <h2 style="text-align: center; margin: 40px 0 20px;">SÁCH MỚI</h2>
                <div class="new-books" id="bookSlider">
                    <c:forEach var="book" items="${newBooks}">
                        <a href="${pageContext.request.contextPath}/bookdetail?id=${book.id}" class="book-link">
                            <div class="book-item">
                                <c:set var="discountValue" value="${empty book.discount ? 0 : book.discount}" />
                                <c:if test="${discountValue > 0}">
                                    <div class="book-discount">-${discountValue}%</div>
                                </c:if>

                                <div class="book-image-wrapper">
                                    <img src="${book.imageURL}" alt="${book.title}">
                                </div>

                                <div class="book-title">${book.title}</div>

                                <c:set var="finalPrice" value="${book.price * (1 - discountValue / 100.0)}" />

                                <div class="book-price">
                                    <c:choose>
                                        <c:when test="${discountValue > 0}">
                                            <span class="price-sale">
                                                <fmt:formatNumber value="${finalPrice}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                            </span>
                                            <span class="price-original">
                                                <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="price-sale">
                                                <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>


            </div>
            <div><button class="backToTop" title="Lên đầu trang">↑</button>
            </div>
        </div>

        <script>
            let currentSlide = 0;
            const slides = document.querySelectorAll('.slider-container-alt .slide');
            function showSlide(index) {
                slides.forEach((slide, i) => slide.classList.toggle('active', i === index));
            }
            function changeSlide(direction) {
                currentSlide += direction;
                if (currentSlide < 0)
                    currentSlide = slides.length - 1;
                if (currentSlide >= slides.length)
                    currentSlide = 0;
                showSlide(currentSlide);
            }
            setInterval(() => changeSlide(1), 3000);

            $(document).ready(function () {
                $('#bookSlider').slick({
                    slidesToShow: 4,
                    slidesToScroll: 1,
                    infinite: true,
                    arrows: true,
                    dots: false,
                    responsive: [
                        {breakpoint: 1024, settings: {slidesToShow: 3}},
                        {breakpoint: 768, settings: {slidesToShow: 2}},
                        {breakpoint: 480, settings: {slidesToShow: 1}}
                    ]
                });
            });

            const backToTopBtn = document.querySelector(".backToTop");

            window.onscroll = function () {
                if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {
                    backToTopBtn.style.display = "block";
                } else {
                    backToTopBtn.style.display = "none";
                }
            };

            backToTopBtn.onclick = function () {
                window.scrollTo({top: 0, behavior: 'smooth'});
            };
        </script>

        <c:forEach var="category" items="${categories}">
            <div class="category-section">
                <!-- Banner danh mục -->
                <div class="category-banner">
                    <a href="${pageContext.request.contextPath}/categorybooks?categoryId=${category.id}">
                        <img src="image/banner_category_${category.id}.jpg" alt="${category.name}" />
                    </a>
                </div>


                <!-- Tiêu đề và xem thêm -->
                <!-- Tiêu đề ở giữa -->
                <div class="category-header">
                    <h2 class="category-title">${category.name}</h2>
                    <a class="view-more" href="categorybooks?categoryId=${category.id}">Xem thêm >></a>
                </div>



                <!-- Sách theo danh mục -->
                <div class="book-row">
                    <c:set var="categoryBooks" value="${requestScope['categoryBooks_' += category.id]}" />
                    <c:forEach var="book" items="${categoryBooks}">
                        <a href="${pageContext.request.contextPath}/bookdetail?id=${book.id}" class="book-link">
                            <div class="book-card">
                                <div class="book-image-wrapper">
                                    <img src="${book.imageURL}" alt="${book.title}" />
                                </div>

                                <p>${book.title}</p>
                                <p class="book-price-text">
                                    <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true" maxFractionDigits="0" /> đ
                                </p>
                            </div>
                        </a>

                    </c:forEach>
                </div>
            </div>
        </c:forEach>
<!--        <a href="${pageContext.request.contextPath}/chat" class="ai-float-button" title="Trợ lý AI tư vấn sách">
            🤖
        </a>-->-->

        <!-- Chat button -->
        <div class="ai-chat-button" onclick="toggleChatBox()">🤖</div>

        <!-- Chat box -->
        <div class="ai-chat-box" id="chatBox">
            <div class="chat-header">
                <span>Trợ lý AI BookZone</span>
                <button onclick="toggleChatBox()">×</button>
            </div>
            <div class="chat-body" id="chatBody"></div>
            <div class="chat-input-area">
                <input type="text" id="userInput" placeholder="Nhập câu hỏi..." onkeypress="handleKey(event)" />
                <button onclick="sendMessage()">Gửi</button>
            </div>
        </div>



        <script>
            function toggleChatBox() {
                const box = document.getElementById("chatBox");
                box.style.display = box.style.display === "flex" ? "none" : "flex";
            }

            function handleKey(e) {
                if (e.key === 'Enter') {
                    sendMessage();
                }
            }

            function sendMessage() {
                const input = document.getElementById("userInput");
                const message = input.value.trim();
                if (!message)
                    return;

                appendMessage(message, "user");
                input.value = "";

                fetch("chat", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "message=" + encodeURIComponent(message)
                })
                        .then(res => res.text())
                        .then(data => appendMessage(data, "ai"))
                        .catch(err => appendMessage("⚠️ Lỗi kết nối.", "ai"));
            }

            function appendMessage(text, sender) {
                const chatBody = document.getElementById("chatBody");
                const msg = document.createElement("div");
                msg.className = "chat-message " + (sender === "user" ? "user-message" : "ai-message");
                msg.innerText = text;
                chatBody.appendChild(msg);
                chatBody.scrollTop = chatBody.scrollHeight;
            }
        </script>



    </body>

</html>