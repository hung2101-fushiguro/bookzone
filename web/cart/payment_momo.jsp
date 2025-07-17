<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<fmt:formatNumber value="${sessionScope.lastOrder.totalPrice}" type="number" var="formattedPrice" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán MoMo</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                padding: 40px;
                background: linear-gradient(to right, #fbc2eb, #a6c1ee);
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
                color: #d70018;
            }
            p {
                font-size: 16px;
                color: #333;
            }
            button {
                padding: 12px 25px;
                background: #d70018;
                color: white;
                border: none;
                border-radius: 6px;
                font-weight: bold;
                font-size: 16px;
                cursor: pointer;
            }
            button:hover {
                background: #a70013;
            }
        </style>
    </head>
    <body>

        <div class="qr-box">
            <h2>Quét mã để thanh toán bằng MoMo</h2>

            <c:set var="amount" value="${sessionScope.lastOrder.totalPrice}" />
            <c:set var="linkMomo" value="https://me.momo.vn/thanhtoanBookZone?amount=${amount}" />
            <c:set var="qrMomoUrl" value="https://api.qrserver.com/v1/create-qr-code/?data=${linkMomo}&size=250x250" />

            <p>Vui lòng thanh toán <strong>${formattedPrice} ₫</strong> cho đơn hàng.</p>

            <img src="${qrMomoUrl}" alt="QR Code MoMo" />

            <form action="${pageContext.request.contextPath}/confirm-payment" method="post">
                <input type="hidden" name="method" value="MOMO" />
                <button type="submit">Tôi đã thanh toán MoMo</button>
            </form>
        </div>

    </body>
</html>
