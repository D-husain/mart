$(document).ready(function() {
	fetchWishlistData();
	fetchwishlistcount();
});

function fetchWishlistData() {
	$.ajax({
		type: 'GET',
		url: 'api/wishlist/data',
		success: function(wishlistData) {
			const wishlistItemsContainer = $('#wishlistItems');

			wishlistItemsContainer.empty();

			if (wishlistData && wishlistData.length > 0) {
				wishlistData.forEach(function(wishlistItem, index) {
					const wishlistItemHTML = `<div class="col-xxl-2 col-lg-3 col-md-4 col-6 product-box-contain">
					<div class="product-box-3 h-100">
						<div class="product-header">
							<div class="product-image">
								<a href="product-left-thumbnail.html"> 
								<img src="../assets/images/cake/product/2.png" class="img-fluid blur-up lazyload" alt="">
								</a>

								<div class="product-header-top">
									<button class="btn wishlist-button delete" data-id="${wishlistItem.id}">
										<i class="fa-solid fa-xmark"></i>
									</button>
								</div>
							</div>
						</div>
						<div class="product-footer">
							<div class="product-detail">
								<span class="span-name">Vegetable</span> <a
									href="product-left-thumbnail.html">
									<h5 class="name">${wishlistItem.pname}</h5>
								</a> 
								<!-- <select class="border-0 bg-transparent">
									<option value="250ml">250 ml</option>
									<option value="250ml">500 ml</option>
									<option value="250ml">1 kg</option>
								</select>-->

								<h5 class="price">
									<span class="theme-color">₹${wishlistItem.discount_price}</span>
									<del>₹${wishlistItem.price}</del>
								</h5>

								<div class="add-to-cart-box bg-white">
								<form id="addToCartForm${index}">
								 <input type="hidden" name="pid" value="${wishlistItem.productid}">
								  <input type="hidden" name="qty" value="1">
									<button id="addToCartBtn${index}" class="btn btn-add-cart addcart-button theme-color">
										Add <span style="margin-left: 10px;">
										<i class="fa-solid fa-bag-shopping theme-color"></i></span>
									</button>
									 </form>
								</div>
							</div>
						</div>
					</div>
				</div>`;

					wishlistItemsContainer.append(wishlistItemHTML);



				});
			} else {
				// If wishlist is empty, display a message or handle accordingly
				wishlistItemsContainer.html('<div class="emptycart-content text-center">' +
					'<h3 class="title">Unfortunately, Your Wishlist Cart Is Empty</h3>' +
					'<h6 class="sub-title">Please Add Something In your Wishlist Cart</h6>' +
					'</div>');
			}
		},
		error: function(xhr) {
			// Handle errors if necessary
			console.error('Error:', xhr);
		}
	});
}


$(document).ready(function() {
	$('.bg-white').on('click', '[id^="addToCartBtn"]', function(e) {
		e.preventDefault(); // Prevent the default form submission

		var btnId = $(this).attr('id').replace('addToCartBtn', '');
		var formData = $('#addToCartForm' + btnId).serialize(); // Serialize form data

		$.ajax({
			type: 'POST',
			url: '/api/cart/addToCart', // URL for the addToCart endpoint
			data: formData,
			success: function(response) {
				fetchWishlistData();
				fetchwishlistcount();

				if (response === "Product added successfully.") {
					$('#insert').addClass('show');
					setTimeout(function() {
						$('#insert').removeClass('show');
					}, 3000);
				}
			},
			error: function(xhr) {
				if (xhr.status === 401) {
					$('#info').addClass('show');
					setTimeout(function() {
						$('#info').removeClass('show');
					}, 3000);
				} else {
					console.error('Error:', xhr);
					// Handle other errors if needed
				}
			}
		});
	});
});


$('div').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#removeWishlist').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "api/wishlist/delete/" + id,
			cache: false,
			success: function() {
				$('#removeWishlist').modal('hide');
				fetchWishlistData();
				fetchwishlistcount();
				// Set a flag in localStorage to indicate deletion
				$('#delete').addClass('show');
				setTimeout(function() {
					$('#delete').removeClass('show');
				}, 3000);
				// After deleting, check if the cart is empty
				if ($('#wishlistTable tbody tr').length === 0) {
					$('#wishlistTable').empty();
				}
				$('#empty-wishlist').empty();
			},
			error: function(error) {
				console.error('Error deleting category:', error);
				$('#removeWishlist').modal('hide');
			}
		});
	});
});



function fetchwishlistcount() {
	$.ajax({
		type: 'GET',
		url: '/api/wishlist/count', // Assuming the correct endpoint URL
		success: function(size) {
			var wishlistCountElement = $('#wishlistcount');

			if (typeof size === 'number' && !isNaN(size)) {
				wishlistCountElement.text(size);
			} else {
				console.error('Invalid size value received:', size);
				// Handle the case when an invalid size is received (e.g., not a number)
				wishlistCountElement.text('0'); // Show 0 or handle differently as per your requirement
			}
		},
		error: function(xhr, status, error) {
			console.error('Error fetching wishlist count:', status, error);
			// Handle the AJAX request error here, update UI accordingly or show an error message
		}
	});
}

