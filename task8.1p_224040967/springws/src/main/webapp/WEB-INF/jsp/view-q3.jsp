<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Question 3</title></head>
<body>
    <h2>Q3: What is 6 × 7?</h2>
    <form method="post" action="/q3">
        <input type="hidden" name="number1" value="6" />
        <input type="hidden" name="number2" value="7" />
        Answer: <input type="text" name="result" />
        <input type="submit" value="Submit" />
    </form>
    <p style="color:red">${message}</p>
</body>
</html>
