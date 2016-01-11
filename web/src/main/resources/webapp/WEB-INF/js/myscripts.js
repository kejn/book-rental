function getCurrentPath() {
	var pathName = $(location).attr("href");
	var currentPage = pathName.substring(pathName.lastIndexOf('/'),
			pathName.length);
	if (currentPage.lastIndexOf("#") >= 0) {
		currentPage = currentPage.substring(0, currentPage.lastIndexOf("#"));
	}
	var loc = window.location;
    var pathName2 = loc.pathname.substring(0, loc.pathname.lastIndexOf('/'));
	console.log(currentPage);
	console.log(pathName);
	console.log(pathName2);
	return pathName2;
}
