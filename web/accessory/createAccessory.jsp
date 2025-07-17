<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Thêm Phụ Kiện Mới</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f0f2f5;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 700px;
                margin: 50px auto;
                background-color: #ffffff;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                margin-bottom: 30px;
                color: #333;
            }

            label {
                display: block;
                margin-bottom: 6px;
                font-weight: bold;
                color: #444;
            }

            input[type="text"],
            input[type="number"],
            input[type="date"],
            textarea,
            select {
                width: 100%;
                padding: 10px 12px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 14px;
                background-color: #fdfdfd;
                transition: border-color 0.3s;
            }

            input:focus,
            textarea:focus,
            select:focus {
                border-color: #0077cc;
                outline: none;
            }

            button {
                display: block;
                width: 100%;
                padding: 12px;
                background-color: #0077cc;
                color: #fff;
                font-size: 16px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button:hover {
                background-color: #005fa3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Thêm Phụ Kiện Mới</h2>

            <form action="${pageContext.request.contextPath}/adminaccessory?action=create" method="post">
                <label for="name">Tên phụ kiện</label>
                <input type="text" name="name" id="name" required>

                <label for="description">Mô tả</label>
                <textarea name="description" id="description" rows="4"></textarea>

                <label for="price">Giá</label>
                <input type="number" step="0.01" name="price" id="price" required>

                <label for="quantity">Số lượng</label>
                <input type="number" name="quantity" id="quantity" required>

                <label for="img_url">Link ảnh</label>
                <input type="text" name="img_url" id="img_url">

                <label for="categoryId">Danh mục</label>
                <select name="categoryId" id="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>

                <label for="createdAt">Ngày tạo</label>
                <input type="date" name="createdAt" id="createdAt" required>

                <button type="submit">Thêm Phụ Kiện</button>
            </form>
        </div>
    </body>
</html>