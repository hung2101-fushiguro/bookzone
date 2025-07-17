<%-- 
    Document   : footer
    Created on : Jul 16, 2025, 3:12:01 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="site-footer">
    <div class="footer-container">
        <div class="footer-column">
            <h3 class="footer-title">
                <img src="https://img.icons8.com/color/48/000000/books.png" width="24" />
                BookZone
            </h3>
            <p>Chuyên cung cấp sách chất lượng, dịch vụ tận tâm và giao hàng nhanh chóng trên toàn quốc.<br>
                BookZone luôn đồng hành cùng tri thức.</p>
        </div>
        <div class="footer-column">
            <h3 class="footer-title">Liên kết nhanh</h3>
            <ul class="footer-links">
                <li><a href="home">Trang Chủ</a></li>
                <li><a href="cart">Giỏ Hàng</a></li>
                <li><a href="login">Đăng Nhập</a></li>
                <li><a href="register">Đăng Ký</a></li>
            </ul>
        </div>
        <div class="footer-column">
            <h3 class="footer-title">Liên hệ</h3>
            <ul class="footer-contact">
                <li>📞 0938 424 289</li>
                <li>✉️ bookzone@gmail.com</li>
                <li>📍 FPT Đà Nẵng</li>
            </ul>
        </div>
    </div>
    <div class="footer-bottom">
        © 2025 BookZone. All rights reserved.
    </div>
</footer>

<style>
    .site-footer {
        background: linear-gradient(90deg, #2193b0, #6dd5ed);
        color: #fff;
        padding: 0;
        margin: 0;
        width: 100%;
        box-sizing: border-box;
    }

    .footer-container {
        max-width: 1200px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
        text-align: center;
        padding: 20px 10px;
        gap: 20px;
    }

    .footer-column {
        flex: 1 1 200px;
        min-width: 200px;
        max-width: 300px;
    }

    .footer-title {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 10px;
        border-bottom: 2px solid #fff;
        display: inline-block;
        padding-bottom: 4px;
    }

    .footer-column p {
        line-height: 1.5;
        font-size: 14px;
        margin: 0 auto;
    }

    .footer-links,
    .footer-contact {
        list-style: none;
        padding: 0;
        margin: 0;
        font-size: 14px;
    }

    .footer-links li,
    .footer-contact li {
        margin: 6px 0;
    }

    .footer-links a {
        color: #fff;
        text-decoration: none;
        transition: color 0.3s;
    }

    .footer-links a:hover {
        text-decoration: underline;
        color: #e0f7fa;
    }

    .footer-bottom {
        text-align: center;
        padding: 10px 0;
        background: linear-gradient(90deg, #2193b0, #6dd5ed);
        font-size: 13px;
        margin: 0;
    }

    @media (max-width: 768px) {
        .footer-container {
            flex-direction: column;
            align-items: center;
        }

        .footer-column {
            max-width: 90%;
            margin-bottom: 15px;
        }

        .footer-title {
            border-bottom: none;
        }
    }

</style>




