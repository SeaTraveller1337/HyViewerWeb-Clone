const btn1_ctn = document.getElementsByClassName("btn1_container")[0];
const one = document.getElementsByClassName("one")[0];
btn1_ctn.addEventListener("click", () => {
  one.classList.toggle("inactive1");
});
