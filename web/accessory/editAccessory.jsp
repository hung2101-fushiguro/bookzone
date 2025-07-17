<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Chỉnh Sửa Phụ Kiện</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f2f4f8;
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
                margin-bottom: 5px;
                font-weight: bold;
                color: #333;
            }

            .old-value {
                font-style: italic;
                color: #777;
                margin-bottom: 5px;
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
            }

            button:hover {
                background-color: #005fa3;
            }

            img {
                max-width: 200px;
                margin-bottom: 10px;
                border-radius: 6px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chỉnh Sửa Phụ Kiện</h2>

            <form action="${pageContext.request.contextPath}/adminaccessory?action=edit" method="post">
                <input type="hidden" name="id" value="${accessory.id}" />

                <label>Tên hiện tại:</label>
                <p class="old-value">${accessory.name}</p>
                <label for="name">Tên mới</label>
                <input type="text" name="name" id="name" value="${accessory.name}" required>

                <label>Mô tả hiện tại:</label>
                <p class="old-value">${accessory.description}</p>
                <label for="description">Mô tả mới</label>
                <textarea name="description" id="description" rows="4">${accessory.description}</textarea>

                <label>Giá hiện tại:</label>
                <p class="old-value">${accessory.price}</p>
                <label for="price">Giá mới</label>
                <input type="number" step="0.01" name="price" id="price" value="${accessory.price}" required>

                <label>Số lượng hiện tại:</label>
                <p class="old-value">${accessory.quantity}</p>
                <label for="quantity">Số lượng mới</label>
                <input type="number" name="quantity" id="quantity" value="${accessory.quantity}" required>

                <label>Ảnh hiện tại:</label>
                <c:if test="${not empty accessory.imageUrl}">
                    <img src="${accessory.imageUrl}" alt="Ảnh phụ kiện">
                </c:if>
                <label for="img_url">Link ảnh mới</label>
                <input type="text" name="img_url" id="img_url" value="${accessory.imageUrl}">

                <label>Danh mục hiện tại:</label>
                <p class="old-value">${accessory.category.name}</p>
                <label for="categoryName">Chọn danh mục mới</label>
                <select name="categoryName" id="categoryName" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.name}" <c:if test="${category.name == accessory.category.name}">selected</c:if>>${category.name}</option>
                    </c:forEach>
                </select>

                <label for="createdAt">Ngày tạo hiện tại:</label>
                <p class="old-value">${accessory.createdAt}</p>
                <label for="createdAt">Ngày tạo mới</label>
                <input type="date" name="createdAt" id="createdAt" value="${accessory.createdAt}" required>

                <button type="submit">Cập nhật Phụ Kiện</button>
            </form>
        </div>
    </body>
</html>
