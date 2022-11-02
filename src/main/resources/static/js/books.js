$(function() {
	refresh();

	function refresh() {
		const queryString = $(".query-string").text();
		$.get("/api/books?" + queryString, processBooks);
	}

	function processBooks(page) {
		updateQueryString(page);
		renderTable(page);
		renderPaginationButtons(page);
	}

	function updateQueryString(page) {
		$(".query-string").text(`page=${page.number}&size${page.size}`);
	}

	function renderTable(page) {
		const $tbody = $("tbody");
		$tbody.empty();
		page.content.forEach(b => renderTableRow(b, $tbody));
	}

	function renderTableRow(b, $tbody) {
		const $row = $(`
			<tr id="row-${b.id}">
				<td>${b.title}</td>
				<td>${b.author}</td>
				<td>${b.isbn}</td>
				<td>
					<button class="view">View</button>
					<button class="edit">Edit</button>
					<button class="delete">Delete</button>
				</td>
			</tr>
			`);
		$row.find(".view").on("click", () => window.location.href = `/books/${b.id}`);
		$row.find(".edit").on("click", () => window.location.href = `/books/${b.id}/edit`);
		$row.find(".delete").on("click", () => $.ajax({
			type: "DELETE",
			url: `/api/books/${b.id}`,
			success: refresh
		}));
		$tbody.append($row);
	}

	function renderPaginationButtons(page) {
		$(".pagination button").off();
		const prevPage = Math.max(0, page.number - 1);
		const lastPage = page.totalPages - 1;
		const nextPage = Math.min(lastPage, page.number + 1);
		$(".first").on("click", () => $.get(`/api/books?page=0&size=${page.size}`, processBooks));
		$(".prev").on("click", () => $.get(`/api/books?page=${prevPage}&size=${page.size}`, processBooks));
		$(".current").text(page.number + 1);
		$(".next").on("click", () => $.get(`/api/books?page=${nextPage}&size=${page.size}`, processBooks));
		$(".last").on("click", () => $.get(`/api/books?page=${lastPage}&size=${page.size}`, processBooks));
	}
});
