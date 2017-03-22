function _change() {
	$("#vCode").attr("src", "/goods1/VerifyCodeServlet?" + new Date().getTime());
}