<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <title>Quản lý đơn hàng</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                padding: 20px;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                background-color: #fff;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }

            th, td {
                border: 1px solid #dee2e6;
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #007bff;
                color: white;
            }

            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            ul {
                margin: 0;
                padding-left: 20px;
            }

            li {
                margin-bottom: 6px;
            }

            img {
                border-radius: 4px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.2);
            }

            select, button {
                padding: 6px 10px;
                margin-left: 5px;
                border-radius: 4px;
                border: 1px solid #ccc;
                font-size: 14px;
            }

            button {
                background-color: #28a745;
                color: white;
                border: none;
                cursor: pointer;
            }

            button:hover {
                background-color: #218838;
            }

            select {
                background-color: #f8f9fa;
            }

            td[colspan="7"] {
                background-color: #fcfcfc;
                border-top: none;
            }

            strong {
                display: block;
                margin-bottom: 6px;
                color: #333;
            }
        </style>
    </head>
    <body>
        <h2>Danh sách đơn hàng</h2>
        <table>
            <tr>
                <th>Mã đơn</th>
                <th>Người dùng</th>
                <th>Địa chỉ</th>
                <th>Giá trị</th>
                <th>Ngày tạo</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.userName}</td>
                    <td>${order.address}</td>
                    <td><fmt:formatNumber value="${order.totalPrice}" type="number"/> ₫</td>
                    <td><fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                    <td>${order.status}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/updatestatus">
                            <input type="hidden" name="orderId" value="${order.id}" />
                            <select name="status">
                                <option ${order.status == 'đang xử lý' ? 'selected' : ''}>đang xử lý</option>
                                <option ${order.status == 'đang vận chuyển' ? 'selected' : ''}>đang vận chuyển</option>
                                <option ${order.status == 'đã nhận' ? 'selected' : ''}>đã nhận</option>
                            </select>
                            <button type="submit">Cập nhật</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td colspan="7">
                        <strong>Sách đã đặt:</strong>
                        <ul>
                            <c:forEach var="detail" items="${orderDetailsMap[order.id]}">
                                <li>
                                    <img src="${detail.bookImage}" width="40" height="60" style="vertical-align: middle;" />
                                    <span style="margin-left: 10px;">
                                        ${detail.bookTitle} - SL: ${detail.quantity}, Giá: 
                                        <fmt:formatNumber value="${detail.price}" type="number" /> ₫
                                    </span>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
