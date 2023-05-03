<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>
<head>
    <title><@spring.message title!"title.default"/></title>
    <link rel="stylesheet" type="text/css" href="${staticMediaUrl}/css/main.css?ts=${launchTimestamp!'0'}">
    <link rel="stylesheet" type="text/css" href="${staticMediaUrl}/css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="${staticMediaUrl}/css/jquery-ui.structure.css"/>
    <link rel="stylesheet" type="text/css" href="${staticMediaUrl}/css/jquery-ui.theme.css"/>
    <script src="${staticMediaUrl}/js/jquery-3.1.1.js"></script>
    <script src="${staticMediaUrl}/js/jquery-ui.js"></script>
    <script src="${staticMediaUrl}/js/jquery.tmpl.js"></script>
    <script src="${staticMediaUrl}/js/main.js?ts=${launchTimestamp!'0'}"></script>
    <#if scripts??>
        <#list scripts as script>
            <script src="${staticMediaUrl}/js/${script}.js?ts=${launchTimestamp!'0'}"></script>
        </#list>
    </#if>
    <script type="text/javascript">
		function translateAjaxError(jqXHR, exception) {
			console.log('An error occurred.');
			console.log(jqXHR);
			let msg = '';
			if (jqXHR.status === 0) {
				<#assign msg><@spring.message 'err.ajax.no.connection'/></#assign>
				msg = "${msg?js_string}";
			} else if (jqXHR.status === 400) {
				<#assign msg><@spring.message 'err.bad.request.400'/></#assign>
				msg = "${msg?js_string}";
			} else if (jqXHR.status === 401) {
				<#assign msg><@spring.message 'err.unauthorized.401'/></#assign>
				msg = "${msg?js_string}";//'Not connect.\n Verify Network.';
			} else if (jqXHR.status === 403) {
				<#assign msg><@spring.message 'err.forbidden.403'/></#assign>
				msg = "${msg?js_string}";//'Not connect.\n Verify Network.';
			} else if (jqXHR.status === 404) {
				<#assign msg><@spring.message 'err.resource.not.found.404'/></#assign>
				msg = "${msg?js_string}";//'Not connect.\n Verify Network.';
			} else if (jqXHR.status === 500) {
				<#assign msg><@spring.message "err.internal.server.error.500"/></#assign>
				msg = "${msg?js_string}";//'Internal Server Error [500].';
			} else if (exception === 'parsererror') {
				<#assign msg><@spring.message 'err.ajax.json.parser.error'/></#assign>
				msg = "${msg?js_string}";//'Requested JSON parse failed.';
			} else if (exception === 'timeout') {
				<#assign msg><@spring.message 'err.ajax.timeout'/></#assign>
				msg = "${msg?js_string}";//'Time out error.';
			} else if (exception === 'abort') {
				<#assign msg><@spring.message 'err.ajax.aborted'/></#assign>
				msg = "${msg?js_string}";//'Ajax request aborted.';
			} else {
				<#assign msg><@spring.message 'err.ajax.unrecognized'/></#assign>
				var respText = jqXHR.responseText;
				if (respText) {
					respText = respText.replace(/(.+<body(\s[^>]+>|>))(.+)(<\/body>.+)/, "$3")
				}
				msg = "${msg?js_string}" + '<br/>' + respText;//'Uncaught Error.\n' + jqXHR.responseText;
			}
			return msg;
		}

		globals = {
			confirmation: {
				title: "<@spring.message 'confirmation.title'/>",
				yesButton: "<@spring.message 'confirmation.yes'/>",
				noButton: "<@spring.message 'confirmation.no'/>"
			},
			messageBox: {
				title: "<@spring.message 'alert.title'/>",
				okButton: "<@spring.message 'button.caption.ok'/>"
			}
		}
    </script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script id="formErrorsTemplate" type="text/x-jQuery-tmpl">
        <div class="form-error-enumeration">
        <ul>
            {{each(i, err) validation_errors}}
            <li>
            <div class="form-error">${r'${err.message}'}</div>
            </li>
            {{/each}}
        </ul>
        <div>

    </script>
</head>