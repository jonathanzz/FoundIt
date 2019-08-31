<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Job List</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		<c:choose>
			<c:when
				test="${empty requestScope.candidates}">
				<div style="margin-top: 100px;" align="center">
					<p>The candidate list is empty.</p>
				</div>
			</c:when>
			<c:otherwise>
				<div style="margin-top: 100px;" align="center">
					<form action="searchjob" method="post">
						<table class="table table-bordered">
							<tr>
								<td>#</td>
								<td><b>Company</b></td>
								<td><b>Position</b></td>
							</tr>
							<%-- <c:set var="each">4</c:set>
							<c:set var="beg" value="${requestScope.pageId * each}"></c:set>
							<c:set var="end" value="${(requestScope.pageId+1) * each - 1}"></c:set>
							<c:set var="total"
								value="${fn:length(requestScope.result) / each}"></c:set>
							<fmt:formatNumber var="total"
								value="${total + (total % 1 == 0 ? 0:0.5)}" type="number"
								pattern="#"></fmt:formatNumber> --%>
							<c:set var="n">1</c:set>
							<c:forEach var="candidate"
								items="${requestScope.candidates.getList}" begin="${beg}"
								end="${end}" step="1">
								<tr>
									<td><c:out value="${n}"></c:out></td>
									<td><a href="candidatedetail?email=${candidate.email}">${candidate.email}</a></td>
								</tr>
								<c:set var="n" value="${n + 1}"></c:set>
							</c:forEach>
						</table>
						<%-- <c:set var="pglen">3</c:set>
						<c:choose>
							<c:when test="${total < pglen}">
								<c:set var="pgbeg" value="1"></c:set>
								<c:set var="pgend" value="${total}"></c:set>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${(requestScope.pageId + 1 + (pglen - 1) / 2) <= total}">
										<c:set var="pgbeg"
											value="${requestScope.pageId + 1 - (pglen - 1) / 2 > 1 ? requestScope.pageId + 1 - (pglen - 1) / 2 : 1}"></c:set>
										<c:set var="pgend" value="${pgbeg + pglen - 1}"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="pgend" value="${total}"></c:set>
										<c:set var="pgbeg" value="${pgend - pglen + 1}"></c:set>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:if test="${pgbeg > 1}">
							<a class="btn btn-default"
								href="showjoblist?key=${requestScope.key}&value=${requestScope.value}&pageId=0">F</a>
						</c:if>
						<c:forEach var="page" begin="${pgbeg}" end="${pgend}" step="1">
							<c:if test="${page == (requestScope.pageId + 1)}">
								<a class="btn btn-primary"
									href="showjoblist?key=${requestScope.key}&value=${requestScope.value}&pageId=${page - 1}">${page}</a>
							</c:if>
							<c:if test="${page != (requestScope.pageId + 1)}">
								<a class="btn btn-default"
									href="showjoblist?key=${requestScope.key}&value=${requestScope.value}&pageId=${page - 1}">${page}</a>
							</c:if>
						</c:forEach>
						<c:if test="${pgend < total}">
							<a class="btn btn-default"
								href="showjoblist?key=${requestScope.key}&value=${requestScope.value}&pageId=${total - 1}">L</a>
						</c:if> --%>
					</form>
				</div>
			</c:otherwise>
		</c:choose>
		<a href="createjob.jsp">Add New Job</a>
		<div class="footer">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>