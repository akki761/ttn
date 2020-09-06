$(document).ready(function () {

    function getPageList(path, limit, offset, el, paginationId){
        $.ajax({
            url : "/bin/find/pagelist",
            method : "GET",
            data : {path : path, limit: limit, offset: offset},
            success : function (data) {
                createPagination(el, JSON.parse(data), limit, offset);
                addPaginationEvent(el, path, limit);
                $(paginationId).addClass('active');
            },
            error : function (error) {
                console.log(error)
            }
        });
    }

    function createPagination(el, result, limit){
        $(el).append("<ul class='page--list'></ul>");
        $.each(result.pageModelList, function (index, value) {
            if(value){
                let html = "<li>"+ "<span>"+ value.title+"</span>" + "<span>"+ value.modified+"</span>" +
                    "<span>"+ value.modifiedBy+"</span>" +"</li>";
                $(el).find(".page--list").append(html);
            }
        });
        createPaginationNumber(el, result.totalResult, limit);
    }

    function createPaginationNumber(el, totalResult, limit){
        let paginationCount = Math.ceil(totalResult/limit);
        $(el).find(".page--list").after("<div class='pagination--number'></div>");
        for(let i=1; i<=paginationCount; i++){
            let html = "<a href='javascript:void(0);' id='count--" + i + "'>" + i + "</a>"
            $(el).find('.pagination--number').append(html);
        }
    }

    function addPaginationEvent(el, path, limit){
        $(el).find('.pagination--number a').click(function(){
            let val = $(this).text();
            let el1 = $(this).closest('.pagination--container');
            el1.find('.page--list').remove();
            let el2 = $(this).closest('.pagination--number');
            el2.remove();
            getPageList(path, limit, limit * (val-1), el1, el2.find("#count--"+val));
        });
    }

    $('.pagination--container').each(function(){
        let path = $(this).data("path");
        let limit = $(this).data("limit");
        getPageList(path, limit, 0,this);
    });
});