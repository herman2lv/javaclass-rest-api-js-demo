$(function() {
	const $createBtn = $("button.create");
	$createBtn.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		const title = $("#input-title").val();
		const author = $("#input-author").val();
		const isbn = $("#input-isbn").val();
		const book = {title, author, isbn};
		$.ajax({
			type: "POST",
			url: "/api/books",
			data: JSON.stringify(book),
			success: processCreated,
			error: processError,
			dataType: "json",
			contentType: "application/json"
		});
	}

	function processCreated(data, status, $XHR) {
		$(".error").remove();
		if ($XHR.status === 201) {
			const uri = $XHR.getResponseHeader("Location");
			console.log("Received location: ", uri);
			window.location.href = uri;
		} else {
			alert("Couldn't create book. Server error");
			console.log("ERROR:", data);
		}
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
