<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>
<fmt:setLocale value="vi_VN" />
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đơn hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 30px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            color: #27c6ee;
            text-align: center;
            margin-bottom: 30px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 15px;
            color: #333;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .payment-options {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .payment-card {
            width: 30%;
            text-align: center;
            border: 2px solid #ddd;
            padding: 15px;
            border-radius: 10px;
            background-color: #f0faff;
        }

        .payment-card img {
            height: 50px;
            margin-bottom: 10px;
        }

        .buttons {
            text-align: center;
            margin-top: 30px;
        }

        button {
            background: linear-gradient(90deg, #2193b0, #6dd5ed);
            color: white;
            border: none;
            border-radius: 6px;
            padding: 12px 25px;
            font-weight: bold;
            font-size: 16px;
            margin: 0 10px;
            cursor: pointer;
        }

        button:hover {
            background: linear-gradient(90deg, #1a7a8f, #5cc5e0);
        }

        .info {
            margin-top: 30px;
            background: #f8f9fa;
            padding: 15px;
            border-left: 4px solid #27c6ee;
        }

        .info p {
            margin: 8px 0;
        }

        .error-message {
            color: red;
            text-align: center;
            margin: 15px 0;
        }

        /* === Bảng chi tiết giỏ hàng === */
        .cart-details {
            margin-top: 40px;
        }

        .cart-details h3 {
            color: #00bcd4;
            margin-bottom: 15px;
        }

        .cart-table {
            width: 100%;
            border-collapse: collapse;
        }

        .cart-table th, .cart-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .cart-table th {
            background-color: #e8f6ff;
            color: #333;
        }

        .cart-table tr:last-child {
            font-weight: bold;
            background-color: #f9f9f9;
        }

        .cart-table td:last-child {
            text-align: right;
        }

        .cart-table td:nth-child(3),
        .cart-table td:nth-child(4) {
            text-align: right;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Xác nhận thông tin đơn hàng</h2>

    <c:if test="${not empty requestScope.error}">
        <div class="error-message">${requestScope.error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/checkout" method="post">
        <label for="address">Địa chỉ nhận hàng:</label>
        <input type="text" id="address" name="address"
               value="${sessionScope.user.address != null ? sessionScope.user.address : ''}"
               required placeholder="Nhập địa chỉ của bạn">

        <label for="phone">Số điện thoại:</label>
        <input type="text" id="phone" name="phone"
               value="${sessionScope.user.phone != null ? sessionScope.user.phone : ''}"
               required placeholder="Nhập số điện thoại của bạn">

        <label>Chọn phương thức thanh toán:</label>
        <div class="payment-options">
            <label class="payment-card" for="cod">
                <input type="radio" id="cod" name="paymentMethod" value="COD" checked>
                <img src="https://www.shutterstock.com/image-vector/simple-vector-red-cod-cash-260nw-2261348455.jpg" alt="COD">
                <span>Thanh toán COD</span>
            </label>

            <label class="payment-card" for="vnpay">
                <input type="radio" id="vnpay" name="paymentMethod" value="VNPAY">
                <img src="https://vinadesign.vn/uploads/images/2023/05/vnpay-logo-vinadesign-25-12-57-55.jpg" alt="VNPay">
                <span>VNPay</span>
            </label>

            <label class="payment-card" for="momo">
                <input type="radio" id="momo" name="paymentMethod" value="MOMO">
                <img src="https://upload.wikimedia.org/wikipedia/vi/f/fe/MoMo_Logo.png" alt="Momo">
                <span>Momo</span>
            </label>
        </div>

        <div class="buttons">
            <button type="submit" name="action" value="update">Cập nhật thông tin</button>
            <button type="submit" name="action" value="confirm">Xác nhận đặt hàng</button>
        </div>
    </form>

    <div class="info">
        <p><strong>Tên khách hàng:</strong> ${sessionScope.user.name}</p>
        <p><strong>Email:</strong> ${sessionScope.user.email}</p>
        <p><strong>Thời gian đặt hàng:</strong> <fmt:formatDate value="${now}" pattern="HH:mm dd/MM/yyyy" /></p>
    </div>

    <c:if test="${not empty cart}">
        <div class="cart-details">
            <h3>Chi tiết giỏ hàng</h3>
            <table class="cart-table">
                <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="total" value="0"/>
                <c:forEach var="item" items="${cart}">
                    <tr>
                        <td>${item.book.title}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.book.price}" type="number" groupingUsed="true"/> đ</td>
                        <td>
                            <fmt:formatNumber value="${item.book.price * item.quantity}" type="number" groupingUsed="true"/> đ
                            <c:set var="total" value="${total + item.book.price * item.quantity}" />
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3" style="text-align: right;">Tổng cộng:</td>
                    <td><fmt:formatNumber value="${total}" type="number" groupingUsed="true"/> đ</td>
                </tr>
                </tbody>
            </table>
        </div>
    </c:if>
</div>
</body>
</html>
