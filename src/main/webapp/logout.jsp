<script>
    sessionStorage.removeItem("role");
</script>
<%
    session.invalidate();
    response.sendRedirect("index.html");
%>
