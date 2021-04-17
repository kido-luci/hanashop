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
    <title>Hana Shop - Cart</title>

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
                    <a class="mobile-show search-bar-icon" href="#"><i class="fas fa-search"></i></a>
                    <div class="mobile-menu"></div>
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

<!-- products -->
<div class="cart-section mt-150 mb-150">
    <div class="container">
        <c:set var="shoppingCart" value="${sessionScope.shoppingCart}"/>
        <c:choose>
            <c:when test="${not empty shoppingCart}">
                <div class="row">
                    <div class="col-lg-8 col-md-12">
                        <div class="cart-table-wrap">
                            <table class="cart-table">
                                <thead class="cart-table-head">
                                <tr class="table-head-row">
                                    <th class="product-remove"></th>
                                    <th class="product-image">Product Image</th>
                                    <th class="product-name">Name</th>
                                    <th class="product-price">Price</th>
                                    <th class="product-quantity">Quantity</th>
                                    <th class="product-total">Total</th>
                                </tr>
                                </thead>

                                <c:set var="listShoppingItemId" value="${shoppingCart.keySet()}"/>
                                <c:set var="shoppingItemHashMap" value="${requestScope.shoppingItemHashMap}"/>
                                <c:set var="total" value="0"/>
                                <tbody>
                                <c:forEach var="shoppingItemID" items="${listShoppingItemId}">
                                    <c:set var="shoppingItem" value="${shoppingItemHashMap.get(shoppingItemID)}"/>
                                    <form method="post" action="DispatchServlet?btAction=updateCartItem&updateCartItemMethod=updateCartItemQuantity" id="form-update-quantity">
                                        <input type="hidden" name="cartItemId" value="${shoppingItem.shoppingItemId}">
                                        <tr class="table-body-row">
                                            <td class="product-remove"><a href="DispatchServlet?btAction=updateCartItem&updateCartItemMethod=removeItemFromCart&cartItemId=${shoppingItem.shoppingItemId}"><i class="far fa-window-close"></i></a></td>
                                            <td class="product-image"><img src="${shoppingItem.shoppingItemImage}" alt=""></td>
                                            <td class="product-name">${shoppingItem.shoppingItemName}</td>
                                            <td class="product-price">$${shoppingItem.shoppingItemPrice}</td>
                                            <td class="product-quantity"><input onchange="submit()" type="number" placeholder="0" name="cartItemQuantity" value="${shoppingCart.get(shoppingItemID)}"></td>
                                            <td class="product-total">$${shoppingItem.shoppingItemPrice * shoppingCart.get(shoppingItemID)}</td>
                                            <c:set var="total" value="${total + (shoppingItem.shoppingItemPrice * shoppingCart.get(shoppingItemID))}"/>
                                        </tr>
                                    </form>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="total-section">
                            <table class="total-table">
                                <thead class="total-table-head">
                                <tr class="table-total-row">
                                    <th>Total</th>
                                    <th>Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr class="total-data">
                                    <td><strong>Subtotal: </strong></td>
                                    <td>$${total}</td>
                                </tr>
                                <tr class="total-data">
                                    <td><strong>Shipping: </strong></td>
                                    <td>$0</td>
                                </tr>
                                <tr class="total-data">
                                    <td><strong>Total: </strong></td>
                                    <td>$${total}</td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="cart-buttons">
                                <a href="${pageContext.request.contextPath}" class="boxed-btn">Add More</a>
                                <a href="DispatchServlet?btAction=checkOut" class="boxed-btn black">Check Out</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </div>
</div>
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
</html>