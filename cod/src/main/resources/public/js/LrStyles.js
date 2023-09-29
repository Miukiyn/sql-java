function moveSlider(sliderItemsContainerPATH, direction, children){
    let sliderItemsContainer = document.querySelector(sliderItemsContainerPATH, direction);
    let sliderItemsContainerLeft = Number(sliderItemsContainer.style.left.replace("px", ""));
    let sliderItemsContainerWidth = Number(sliderItemsContainer.style.width.replace("px", ""));
    
    let sliderItem = document.querySelector(sliderItemsContainerPATH + " .sliderItem");
    let sliderItemStyle = getComputedStyle(sliderItem);

    let result = 0;    

    let translationValue = Number(getComputedStyle(document.querySelector(sliderItemsContainerPATH + " .sliderItem"))['width'].replace("px", ""))
        + Number(getComputedStyle(sliderItemsContainer)['gap'].replace("px", ""));

    //let translationMaximum = children * translationValue - sliderItemsContainerWidth;

    if(direction == "left" && sliderItemsContainerLeft < 0){
        sliderItemsContainer.style.left = (sliderItemsContainerLeft + translationValue) + "px";
    }

    else if(direction == "right" /*&& sliderItemsContainerLeft >= translationMaximum*/){
        sliderItemsContainer.style.left = (sliderItemsContainerLeft - translationValue) + "px";
    }

    //console.log(sliderItemsContainerWidth);
}

function openAccordion(accordionID){
    let accordion = document.querySelector("#LrAccordion"+ accordionID.toString());
    let accordionButton = document.querySelector("#LrAccordion"+ accordionID.toString() + " .LrAccordionButton");
    let accordionItem = document.querySelector("#LrAccordion"+ accordionID.toString() + " .LrAccordionItem");
    let openHeight = Number(getComputedStyle(accordionItem)["height"].replace("px", "")) + Number(getComputedStyle(accordionButton)["height"].replace("px", ""));
    //let accordion = document.getElementById(accordion + accordionID.toString());
    console.log();
    if(getComputedStyle(accordion)["height"] == getComputedStyle(accordionButton)["height"]){
        console.log(openHeight.toString() + "px");
        accordion.style.height = openHeight.toString() + "px";
        //accordionItem.style.display = "block";
    }
    else{
        accordion.style.height = getComputedStyle(accordionButton)["height"];
        //accordionItem.style.display = "none";
    }
}

function LrListSort(LrListPATH, key){
    LrListItems = document.querySelectorAll("#" + LrListPATH + " .LrListItem");
    LrSearchInput = document.querySelector("#" + LrListPATH + " .LrSearchInput");    
    for(let i = 0; i < LrListItems.length; i++){
        if( LrListItems[i].classList[1].includes(LrSearchInput.value.replaceAll(" ", "").toUpperCase()) == true){
            LrListItems[i].style.display = "block";
        }

        else{
            LrListItems[i].style.display = "none";
        }
    }
}

function LrGoogleMapGoTo(LrGoogleMapId, url){
    let LrGoogleMap = document.getElementById("LrGoogleMap" + LrGoogleMapId);
    LrGoogleMap.src = url;
}

function LrCloseModal(id){
    document.getElementById(id).style.opacity = "0%";
    document.getElementById(id).style.display = "none";    
}

function LrOpenModal(id){
    document.getElementById(id).style.opacity = "100%";
    document.getElementById(id).style.display = "flex";    
}

function LrOpenMobileSidebar(){
    let mobileSideBar = document.getElementById("LrMobileSidebar");
    mobileSideBar.style.display = "flex";
}