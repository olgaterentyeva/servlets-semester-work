let offset = 0;
const sliderLine = document.querySelector('.slider-films');

document.querySelector('.slider-next').addEventListener('click', function (){
    offset = offset + 260;
    if(offset > 790){
        offset = 0;
    }
    sliderLine.style.left = -offset + 'px';
});

document.querySelector('.slider-prev').addEventListener('click', function (){
    offset = offset - 260;
    if(offset < 0){
        offset = 790;
    }
    sliderLine.style.left = -offset + 'px';
})


