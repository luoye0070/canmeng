
<script type="text/javascript" charset="utf-8">
  function logout(b) {
          $('#sessionInfoDiv').html('');
          $.post('${resource()}/adminInfo/logout', function() {
                  if (b) {
                          if (sy.isLessThanIe8()) {
                                  loginDialog.dialog('open');
                          } else {
                                  location.replace("${resource()}/");
                          }
                  } else {
                          loginDialog.dialog('open');
                  }
          });
  }
</script>

<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
	<g:if test="${session.username != null}"><a href="#"  onclick="logout(true);">退出系统</a>[<strong>${session.username}</strong>]，欢迎你！</g:if>

</div>
<g:render template="login"/>