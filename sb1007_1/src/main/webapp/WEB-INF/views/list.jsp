<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <style>
        body {
            background-color: #ffebcd; /* 부드러운 크림색 배경 */
            font-family: 'Comic Sans MS', cursive; /* 귀여운 글꼴 지정 */
        }
        table {
            background-color: #fff8dc; /* 크림색 배경 테이블 */
            border: 2px solid #f0e68c; /* 황금색 테두리 */
            border-radius: 10px; /* 둥근 테두리 */
        }
        td {
            padding: 10px; /* 셀 패딩 */
        }
        input[type="text"] {
            border: 2px solid #dda0dd; /* 보랏빛 테두리 */
            border-radius: 5px; /* 둥근 테두리 */
            padding: 5px; /* 패딩 */
            width: calc(100% - 20px); /* 너비 */
            font-family: 'Comic Sans MS', cursive; /* 글꼴 적용 */
        }
        input[type="submit"] {
            background-color: #dda0dd; /* 보랏빛 배경 */
            border: none; /* 테두리 없음 */
            padding: 10px 20px; /* 패딩 */
            border-radius: 10px; /* 둥근 테두리 */
            cursor: pointer; /* 커서 포인터 */
            font-weight: bold; /* 진한 글씨 */
            font-family: 'Comic Sans MS', cursive; /* 글꼴 적용 */
        }
        a {
            text-decoration: none; /* 밑줄 없음 */
            color: #ff69b4; /* 분홍색 글씨 */
            font-weight: bold; /* 진한 글씨 */
            font-family: 'Comic Sans MS', cursive; /* 글꼴 적용 */
        }
    </style>
</head>
<body>

<table width="500" cellpadding="0" cellspacing="0" border="1">
    <tr>
        <td>번호</td>
        <td>작성자</td>
        <td>제목</td>
        <td>삭제</td>
    <tr>
    <c:forEach items="${list}" var="dto">
    <tr>
        <td>${dto.id}</td>
        <td>${dto.writer}</td>
        <td><a href="view?id=${dto.id}">${dto.title}</a></td>
        <td><a href="delete?id=${dto.id}">X</a></td>
    <tr>
    </c:forEach>
</table>

<p><a href="writeForm">글작성</a></p>

</body>
</html>