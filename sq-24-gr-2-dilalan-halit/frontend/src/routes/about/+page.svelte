<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<div class="flex min-h-full flex-col justify-center px-6 py-6 lg:px-8">
	<div class="sm:mx-auto sm:w-full sm:max-w-sm">
		<img class="mx-auto h-20 w-auto" src="shopping-icon.png" alt="E-Store">
		<h2 class="mt-2 mb-6 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">About this app</h2>
	</div>
	<div class="text-column">
		<p class="py-2">
			This project is a simple e-commerce template for the Proseminar
			<a href='https://lfuonline.uibk.ac.at/public/lfuonline_lv.details?sem_id_in=24S&lvnr_id_in=703086'>Software Quality</a>
			at the University of Innsbruck. The goal is to provide students a project onto which they can apply different
			software quality techniques and technologies.
		</p>

		<p class="py-2">
			The project gives the web user the possibility to view products, add them to the cart and to check out the order.
			For ordering a product the user needs to be logged in. An authentication mechanism over JWT and cookies is implemented.
			The web application should also work on mobile devices and tablets as the UI adapts to the screen dimensions.
		</p>

		<p class="py-2">
			Please note that this project is not intended for production use and is only a template for educational purposes.
			It is therefore intended to be suboptimal in certain areas, such as security, performance and usability to allow
			students to improve the code base during the semester.
		</p>

		<p class="py-2">
			Copyright (C) Harald Victor Schweiger, B.Sc. M.Sc. - All Rights Reserved All information contained herein is,
			and remains the property of the author. Unauthorized copying, sharing or using this file,
			via any medium is strictly prohibited unless prior written permission is obtained from the author.
			Proprietary and confidential. Written by Harald Victor Schweiger
			<a href=mailto:harald.schweiger@uibk.ac.at>harald.schweiger@uibk.ac.at</a>, January 2024
		</p>

		<p class="py-2 font-bold">
			Special thanks to the following people and organizations:
		</p>
		<ul class="list-disc pl-5">
			<li>Product Images by Patrick Franz</li>
			<li>Icon by <a href="https://pixabay.com/users/nikin-253338/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=2184065">Nikin</a> from <a href="https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=2184065">Pixabay</a></li>
			<li>UI Templates by <a href="https://tailwindui.com/">TailwindUI</a></li>
		</ul>
	</div>
</div>
