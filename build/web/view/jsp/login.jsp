<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/10/2020
  Time: 9:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Hana Shop - Login</title>

    <!-- favicon -->
    <link rel="shortcut icon" type="image/png" href="assets/img/favicon.png">
    <!-- google font -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Poppins:400,700&display=swap" rel="stylesheet">
    <!-- fontawesome -->
    <link rel="stylesheet" href="assets/css/all.min.css">
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- owl carousel -->
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <!-- magnific popup -->
    <link rel="stylesheet" href="assets/css/magnific-popup.css">
    <!-- animate css -->
    <link rel="stylesheet" href="assets/css/animate.css">
    <!-- mean menu css -->
    <link rel="stylesheet" href="assets/css/meanmenu.min.css">
    <!-- main style -->
    <link rel="stylesheet" href="assets/css/main.css">
    <!-- responsive -->
    <link rel="stylesheet" href="assets/css/responsive.css">

    <script>
        window.history.forward();
    </script>
</head>

<c:choose>
<c:when test="${not empty sessionScope.notification}">
<body onload="load(); showSnackBar();" class="login-bg">
</c:when>
<c:otherwise>
<body onload="load();" class="login-bg">
</c:otherwise>
</c:choose>

<div class="login-container">

    <div class="container" style="margin-top: 15px">
        <div class="row">
            <div class="col-lg-8 offset-lg-2 text-center">
                <div class="breadcrumb-text">
                    <a href="${pageContext.request.contextPath}">
                        <p>Fresh and Organic</p>
                        <h1>Hana Shop</h1>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="login-content">
        <h2 class="title login">Log In</h2>
        <form class="login-form" action="DispatchServlet" method="post">
            <c:set var="error" value="${requestScope.INSERTERS}"/>

            <input type="hidden" name="loginType" value="loginWithAccount">

            <label>
                <c:choose>
                    <c:when test="${empty error.usernameLengthError}">
                        <input type="text" name="txtUserId" placeholder="User Id" value="${param.txtUserId}"/>
                        <div class="line clearfix"></div>
                    </c:when>

                    <c:otherwise>
                        <input type="text" name="txtUserId" placeholder="User Id" value="${param.txtUserId}" class="error"/>
                        <span class="errorMsg">${error.usernameLengthError}</span>
                    </c:otherwise>
                </c:choose>
            </label>

            <label>
                <c:choose>
                    <c:when test="${empty error.passwordLengthError}">
                        <input type="password" name="txtPassword" placeholder="Password" value=""/>
                    </c:when>
                    <c:otherwise>
                        <input type="password" name="txtPassword" placeholder="Password" value="" class="error"/>
                        <span class="errorMsg">${error.passwordLengthError}</span>
                    </c:otherwise>
                </c:choose>
            </label>

            <label>
                <button class="btn" type="submit" value="login" name="btAction">Login With Account</button>
            </label>

            <div class="divider">
                <span>or</span>
            </div>

            <label>
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/hana-shop/DispatchServlet?btAction=login&response_type=code&client_id=24269541526-ogsc7tohojd6fcbhad9seqk86cuc9n2a.apps.googleusercontent.com&approval_prompt=force">
                    <button class="btn color-red" type="button">
                        Login With Google
                        <i class="fab fa-google-plus-g"></i>
                    </button>
                </a>
            </label>
        </form>
    </div>
</div>

<!-- notification -->
<c:if test="${not empty sessionScope.notification}">
    <div id="snackbar">${sessionScope.notification}</div>
    <c:remove var="notification" scope="session"/>
</c:if>

<!-- jquery -->
<script src="assets/js/jquery-1.11.3.min.js"></script>
<!-- bootstrap -->
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<!-- count down -->
<script src="assets/js/jquery.countdown.js"></script>
<!-- isotope -->
<script src="assets/js/jquery.isotope-3.0.6.min.js"></script>
<!-- waypoints -->
<script src="assets/js/waypoints.js"></script>
<!-- owl carousel -->
<script src="assets/js/owl.carousel.min.js"></script>
<!-- magnific popup -->
<script src="assets/js/jquery.magnific-popup.min.js"></script>
<!-- mean menu -->
<script src="assets/js/jquery.meanmenu.min.js"></script>
<!-- sticker js -->
<script src="assets/js/sticker.js"></script>
<!-- main js -->
<script src="assets/js/main.js"></script>

</body>
</html>