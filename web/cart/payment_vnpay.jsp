<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<fmt:formatNumber value="${sessionScope.lastOrder.totalPrice}" type="number" var="formattedPrice" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán VNPAY</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                padding: 40px;
                background: linear-gradient(to right, #b2fefa, #0ed2f7);
            }
            .qr-box {
                background-color: white;
                padding: 30px;
                border-radius: 10px;
                display: inline-block;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            img {
                width: 250px;
                height: 250px;
                margin-bottom: 20px;
            }
            h2 {
                color: #2193b0;
            }
            p {
                font-size: 16px;
                color: #333;
            }
            button {
                padding: 12px 25px;
                background: #2193b0;
                color: white;
                border: none;
                border-radius: 6px;
                font-weight: bold;
                font-size: 16px;
                cursor: pointer;
            }
            button:hover {
                background: #136f8a;
            }
        </style>
    </head>
    <body>

        <div class="qr-box">
            <h2>Quét mã để thanh toán bằng VNPAY</h2>

            <c:set var="amount" value="${sessionScope.lastOrder.totalPrice}" />
            <c:set var="note" value="BookZone-Order-${sessionScope.lastOrder.id}" />
            <c:set var="qrVnpay" value="https://img.vietqr.io/image/VCB-1042120616-compact2.png?amount=${amount}&addInfo=${note}&accountName=NGUYENTIENLOC" />

            <p>Vui lòng thanh toán <strong>${formattedPrice} ₫</strong> cho đơn hàng.</p>

            <img src="${qrVnpay}" alt="QR Code VNPAY" />

            <form action="${pageContext.request.contextPath}/confirm-payment" method="post">
                <input type="hidden" name="method" value="VNPAY" />
                <button type="submit">Tôi đã thanh toán VNPAY</button>
            </form>
        </div>

    </body>
</html>
