<%--
    Document   : shopping
    Created on : Jan 13, 2021, 4:34:01 AM
    Author     : lapl1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Responsive Bootstrap4 Shop Template, Created by Imran Hossain from https://imransdesign.com/">

    <!-- title -->
    <title>Hana Shop</title>

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

</head>
<c:choose>
<c:when test="${not empty sessionScope.notification}">
<body onload="load(); showSnackBar();">
</c:when>
<c:otherwise>
<body onload="load();">
</c:otherwise>
</c:choose>
<!--PreLoader-->
<div class="loader">
    <div class="loader-inner">
        <div class="circle"></div>
    </div>
</div>
<!--PreLoader Ends-->

<!-- header -->
<div class="top-header-area" id="sticker">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-sm-12 text-center">
                <div class="main-menu-wrap">
                    <!-- logo -->
                    <div class="site-logo">
                        <a href="${pageContext.request.contextPath}">
                            <img src="assets/img/logo.png" alt="">
                        </a>
                    </div>
                    <!-- logo -->

                    <!-- menu start -->
                    <nav class="main-menu">
                        <ul>
                            <li>
                                <div class="search-bar">
                                    <div class="search-bar-tablecell">
                                        <form action="DispatchServlet" method="get" style="display: flex">
                                            <input type="hidden" value="${param.minPriceInput}" name="minPriceInput">
                                            <input type="hidden" value="${param.maxPriceInput}" name="maxPriceInput">
                                            <input type="text" placeholder="Search your favorites food" name="txtSearchValue" value="${param.txtSearchValue}">
                                            <button class="search-bar-icon" type="submit" value="searchItem" name="btAction"><i class="fas fa-search"></i></button>
                                        </form>
                                    </div>
                                </div>
                            </li>
                            <li class="header-li">
                                <div class="header-icons">
                                    <a class="shopping-cart" href="DispatchServlet?btAction=viewCart"><i class="fas fa-shopping-cart"></i></a>
                                </div>
                            </li>
                            <li>
                                <div class="account-header">
                                    <c:set var="authenticatedUser" value="${sessionScope.authenticatedUser}"/>
                                    <c:choose>
                                        <c:when test="${empty authenticatedUser}">
                                            <a href="DispatchServlet?btAction=login">Log In</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a>${authenticatedUser.fullName}</a>
                                            <ul class="sub-menu">
                                                <li><a href="DispatchServlet?btAction=history">History</a></li>
                                                <li><a href="DispatchServlet?btAction=logout">Log Out</a></li>
                                            </ul>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </li>
                        </ul>
                    </nav>
                    <!-- menu end -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end header -->

<!-- breadcrumb-section -->
<div class="breadcrumb-section breadcrumb-bg">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 offset-lg-2 text-center">
                <div class="breadcrumb-text">
                    <p>Fresh and Organic</p>
                    <h1>Hana Shop</h1>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end breadcrumb section -->

<!-- single product -->
<div class="single-product mt-150 mb-150">
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <div class="single-product-img">
                    <img src="${requestScope.singleItem.shoppingItemImage}" alt="">
                </div>
            </div>
            <div class="col-md-7">
                <div class="single-product-content">
                    <h3>${requestScope.singleItem.shoppingItemName}</h3>
                    <p class="single-product-pricing"><span>Per Kg</span> $${requestScope.singleItem.shoppingItemPrice}</p>
                    <p>${requestScope.singleItem.shoppingItemDescription}</p>
                    <div class="single-product-form">
                        <a href="DispatchServlet?btAction=addToCart&shoppingItemId=${requestScope.singleItem.shoppingItemId}" class="cart-btn"><i class="fas fa-shopping-cart"></i> Add to Cart</a>
                        <p><strong>Categories: </strong>${requestScope.singleItem.shoppingItemCategory}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end single product -->

<!-- end products -->

<!-- logo carousel -->
<div class="logo-carousel-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="logo-carousel-inner">
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/1.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/2.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/3.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/4.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/5.png" alt="">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end logo carousel -->

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
<script>
    function savePosition(){
        sessionStorage.setItem("position", window.scrollY);
    }
    window.onbeforeunload = savePosition;

    function load(){
        window.scrollTo(0, parseFloat(sessionStorage.getItem("position")));
    }
</script>
</html>