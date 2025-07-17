<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<fmt:formatNumber value="${sessionScope.lastOrder.totalPrice}" type="number" var="formattedPrice" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán COD</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                padding: 60px;
                background: #f5f5f5;
            }
            .cod-box {
                background-color: white;
                padding: 40px;
                border-radius: 10px;
                display: inline-block;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            h2 {
                color: #27c6ee;
                margin-bottom: 20px;
            }
            p {
                font-size: 18px;
                color: #333;
            }
            button {
                padding: 12px 30px;
                background: #27c6ee;
                color: white;
                border: none;
                border-radius: 6px;
                font-weight: bold;
                font-size: 16px;
                cursor: pointer;
            }
            button:hover {
                background: #1a9dcf;
            }
        </style>
    </head>
    <body>

        <div class="cod-box">
            <h2>Thanh toán khi nhận hàng (COD)</h2>
            <p>Vui lòng chuẩn bị <strong>${formattedPrice} ₫</strong> để thanh toán khi nhận hàng.</p>

            <form action="${pageContext.request.contextPath}/confirm-payment" method="post">
                <input type="hidden" name="method" value="COD" />
                <button type="submit">Xác nhận đặt hàng COD</button>
            </form>
        </div>

    </body>
</html>
