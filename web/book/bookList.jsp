<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách sách</title>
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
                background: #4fc3f7;
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

            .search-box, .sort-box {
                display: flex;
                align-items: center;
                gap: 6px;
            }

            .search-box input[type="text"] {
                padding: 6px;
                width: 400px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .search-box button, select {
                padding: 6px 10px;
                border-radius: 4px;
                border: 1px solid #ccc;
                cursor: pointer;
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
            <!-- Nút thêm sách -->
            <div>
                <a class="btn" href="${pageContext.request.contextPath}/admin/admin.jsp">⬅ Quay lại</a>
            </div>
            <div>
                <a class="btn" href="${pageContext.request.contextPath}/books?action=create">+ Thêm sách</a>
            </div>



            <!-- Ô tìm kiếm -->
            <form class="search-box" action="${pageContext.request.contextPath}/books" method="get">
                <input type="text" name="keyword" placeholder="Tìm kiếm sách..." value="${param.keyword}">
                <button type="submit">🔍</button>
            </form>

            <!-- Dropdown sắp xếp -->
            <form class="sort-box" action="${pageContext.request.contextPath}/books" method="get">
                <input type="hidden" name="keyword" value="${param.keyword}"/>

                <label for="sortPrice">Sắp xếp theo giá:</label>
                <select name="sortPrice" id="sortPrice" onchange="this.form.submit()">
                    <option value="">--Chọn--</option>
                    <option value="asc" ${param.sortPrice == 'asc' ? 'selected' : ''}>Tăng dần</option>
                    <option value="desc" ${param.sortPrice == 'desc' ? 'selected' : ''}>Giảm dần</option>
                </select>

                <label for="sortQuantity">Sắp xếp theo số lượng:</label>
                <select name="sortQuantity" id="sortQuantity" onchange="this.form.submit()">
                    <option value="">--Chọn--</option>
                    <option value="asc" ${param.sortQuantity == 'asc' ? 'selected' : ''}>Tăng dần</option>
                    <option value="desc" ${param.sortQuantity == 'desc' ? 'selected' : ''}>Giảm dần</option>
                </select>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tiêu đề</th>
                    <th>Tác giả</th>
                    <th>Mô tả</th>
                    <th>Giá gốc</th>
                    <th>Giảm giá (%)</th>
                    <th>Giá sau giảm</th>
                    <th>Số lượng</th>
                    <th>Ảnh bìa</th>
                    <th>Thể loại</th>
                    <th>Hành động</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>
                    <c:when test="${not empty books}">
                        <c:forEach var="b" items="${books}">
                            <tr>
                                <td>${b.id}</td>
                                <td>${b.title}</td>
                                <td>${b.author}</td>
                                <td class="description">${b.description}</td>

                                <td><fmt:formatNumber value="${b.price}" type="number" groupingUsed="true"/> đ</td>
                                <td>${b.discount}%</td>
                                <td>
                                    <fmt:formatNumber value="${b.price * (100 - b.discount) / 100}" type="number" groupingUsed="true"/> đ
                                </td>

                                <td>${b.quantity}</td>
                                <td>
                                    <c:if test="${not empty b.imageURL}">
                                        <img class="cover-img" src="${b.imageURL}" alt="Ảnh bìa">
                                    </c:if>
                                </td>
                                <td>${b.categoryName}</td>
                                <td>
                                    <div class="action-buttons">
                                        <a class="btn" href="${pageContext.request.contextPath}/books?action=edit&id=${b.id}">️ Sửa</a>
                                        <a class="btn btn-danger" 
                                           href="${pageContext.request.contextPath}/books?action=delete&id=${b.id}" 
                                           onclick="return confirm('Bạn có chắc muốn xóa sách này?');">️ Xóa</a>
                                    </div>
                                </td>
                            </tr>

                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9" style="text-align: center; color: gray;">Không có sách nào trong hệ thống.</td>
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
                            <a href="${pageContext.request.contextPath}/books?page=${i}&sortPrice=${param.sortPrice}&sortQuantity=${param.sortQuantity}">[${i}]</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:if>

    </body>
</html>