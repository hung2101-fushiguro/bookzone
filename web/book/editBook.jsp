<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Chỉnh sửa sách</title>
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
            <h2>Chỉnh sửa Thông Tin Sách</h2>


            <form action="${pageContext.request.contextPath}/books?action=edit" method="post">
                <input type="hidden" name="id" value="${book.id}" />

                <label>Tiêu đề hiện tại:</label>
                <p class="old-value">${book.title}</p>
                <label for="title">Tiêu đề mới</label>
                <input type="text" name="title" id="title" value="${book.title}" required>

                <label>Tác giả hiện tại:</label>
                <p class="old-value">${book.author}</p>
                <label for="author">Tác giả mới</label>
                <input type="text" name="author" id="author" value="${book.author}" required>

                <label>Mô tả hiện tại:</label>
                <p class="old-value">${book.description}</p>
                <label for="description">Mô tả mới</label>
                <textarea name="description" id="description" rows="4">${book.description}</textarea>

                <label>Giá hiện tại:</label>
                <p class="old-value">${book.price}</p>
                <label for="price">Giá mới</label>
                <input type="number" step="0.01" name="price" id="price" value="${book.price}" required>

                <label>Số lượng hiện tại:</label>
                <p class="old-value">${book.quantity}</p>
                <label for="quantity">Số lượng mới</label>
                <input type="number" name="quantity" id="quantity" value="${book.quantity}" required>

                <label>Ảnh hiện tại:</label>
                <c:if test="${not empty book.imageURL}">
                    <img src="${book.imageURL}" alt="Ảnh sách">
                </c:if>
                <label for="img_url">Link ảnh mới</label>
                <input type="text" name="img_url" id="img_url" value="${book.imageURL}">

                <label>Danh mục hiện tại:</label>
                <p class="old-value">${book.categoryName}</p>
                <label for="cateId">Chọn danh mục mới</label>
                <select name="cateId" id="cateId" required>
                    <c:forEach var="cate" items="${categories}">
                        <option value="${cate.id}" <c:if test="${cate.id == book.categoryID}">selected</c:if>>
                            ${cate.name}
                        </option>
                    </c:forEach>
                </select>

                <label>Giảm giá hiện tại:</label>
                <p class="old-value">${book.discount}%</p>
                <label for="discount">Giảm giá mới (%)</label>
                <input type="number" name="discount" id="discount" value="${book.discount}" min="0" max="100">


                <label>Ngày thêm hiện tại:</label>
                <p class="old-value">${book.created_at}</p>
                <label for="dateadd">Ngày thêm mới</label>
                <input type="date" name="dateadd" id="dateadd" value="${book.created_at}" required>

                <button type="submit">Cập nhật Sách</button>
            </form>
        </div>
    </body>
</html>