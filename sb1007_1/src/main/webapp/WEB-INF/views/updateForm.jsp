<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type="text/html; charset=UTF-8">
    <title>귀여운 게시물 수정 페이지</title>
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
<br><p>

<table width="500" cellpadding="0" cellspacing="0" border="1">
    <form action="/update" method="post">
        <input type="hidden" name="id" value=${dto.id}>
        <tr>
            <td> 작성자 </td>
            <td> <input type="text" name="writer" size = "100" value=${dto.writer}> </td>
        </tr>
        <tr>
            <td> 제목 </td>
            <td> <input type="text" name="title" size = "100" value=${dto.title} > </td>
        </tr>
        <tr>
            <td> 내용 </td>
            <td> <input type="text" name="content" size = "100" value=${dto.content} > </td>
        </tr>
        <tr >
            <td colspan="2"> <input type="submit" value="수정하기">
                &nbsp;&nbsp; <a href="list">목록보기</a></td>
        </tr>
    </form>
</table>
</body>
</html>