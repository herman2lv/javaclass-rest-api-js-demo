$(function() {
	const $editBtn = $("button.edit");
	$editBtn.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		const title = $("#input-title").val();
		const author = $("#input-author").val();
		const isbn = $("#input-isbn").val();
		const book = {title, author, isbn};
		const id = $("#input-id").val();
		$.ajax({
			type: "PUT",
			url: `/api/books/${id}`,
			data: JSON.stringify(book),
			success: processUpdated,
			error: processError,
			dataType: "json",
			contentType: "application/json"
		});
	}

	function processUpdated(data) {
		$(".error").remove();
		window.location.href = `/books/${data.id}`;
	}

	function processError(response) {
		$(".error").remove();
		if (response.status === 422) {
			const validationError = response.responseJSON;
			for (const field in validationError.messages) {
				validationError.messages[field].forEach(msg => {
					$("form").prepend($(`<div class="error">${field}: ${msg}</div>`));
				})
			}
		}
	}

});
