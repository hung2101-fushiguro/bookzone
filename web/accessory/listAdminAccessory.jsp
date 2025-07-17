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
                padding: 6px 12px;
                background: #28a745;
                color: white;
                border: none;
                border-radius: 4px;
                text-decoration: none;
                font-size: 14px;
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

            .pagination {
                margin-top: 20px;
                text-align: center;
            }

            .pagination a, .pagination strong {
                margin: 0 5px;
                text-decoration: none;
                color: #007bff;
            }

            .pagination strong {
                font-weight: bold;
                color: black;
            }
        </style>
    </head>
    <body>

        <div class="top-bar">
            <div>
                <a class="btn" href="${pageContext.request.contextPath}/admin/accessory?action=create">+ Thêm phụ kiện</a>
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
                                        <a class="btn" href="${pageContext.request.contextPath}/admin/accessory?action=edit&id=${accessory.id}">Sửa</a>
                                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/accessory?action=delete&id=${accessory.id}" onclick="return confirm('Bạn có chắc muốn xóa phụ kiện này?');">Xóa</a>
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

        <!-- PHÂN TRANG -->
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <strong>[${i}]</strong>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/admin/accessory?page=${i}">[${i}]</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:if>

    </body>
</html>
