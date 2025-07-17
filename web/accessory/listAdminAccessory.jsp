<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Danh Sách Phụ Kiện</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }

            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
                gap: 10px;
                margin-bottom: 20px;
            }

            .btn {
                padding: 12px 18px;
                background-color: #4fc3f7;
                color: white;
                border: none;
                border-radius: 6px;
                text-decoration: none;
                font-size: 16px;
                display: inline-block;
                text-align: center;
                transition: background-color 0.3s ease, transform 0.2s ease-in-out;
            }

            .btn-danger {
                background: #dc3545;
            }

            .btn:hover {
                opacity: 0.85;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            th, td {
                border: 1px solid #ccc;
                padding: 8px;
                vertical-align: top;
            }

            th {
                background: #f5f5f5;
                text-align: center;
            }

            .cover-img {
                max-width: 80px;
                height: auto;
                display: block;
            }

            .description {
                max-width: 300px;
                white-space: pre-wrap;
            }

            .action-buttons {
                display: flex;
                flex-direction: column;
                gap: 4px;
            }
        </style>
    </head>
    <body>

        <div class="top-bar">
            <div>
                <a class="btn" href="${pageContext.request.contextPath}/admin/admin.jsp">⬅Quay lại</a>
            </div>
            <div>
                <a class="btn" href="${pageContext.request.contextPath}/adminaccessory?action=create">+ Thêm phụ kiện</a>
            </div>
            
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên phụ kiện</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Ảnh</th>
                    <th>Danh mục</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty accessories}">
                        <c:forEach var="accessory" items="${accessories}">
                            <tr>
                                <td>${accessory.id}</td>
                                <td>${accessory.name}</td>
                                <td class="description">${accessory.description}</td>
                                <td>${accessory.price}</td>
                                <td>${accessory.quantity}</td>
                                <td><img class="cover-img" src="${accessory.imageUrl}" alt="Ảnh phụ kiện"></td>
                                <td>${accessory.category.name}</td>
                                <td>
                                    <div class="action-buttons">
                                        <a class="btn" href="${pageContext.request.contextPath}/adminaccessory?action=edit&id=${accessory.id}">Sửa</a>
                                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/adminaccessory?action=delete&id=${accessory.id}" onclick="return confirm('Bạn có chắc muốn xóa phụ kiện này?');">Xóa</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" style="text-align: center; color: gray;">Không có phụ kiện nào trong hệ thống.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

    </body>
</html>
