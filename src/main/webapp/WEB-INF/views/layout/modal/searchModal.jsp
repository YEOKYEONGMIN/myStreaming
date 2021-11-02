<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal" id="searchModal" tabindex="-1" aria-labelledby="searchModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <div class="cumstom-input-group" style="display: flex; border: solid 1px black">
                    <div style="flex: 1">
                        <input type="text" class="form-control" placeholder="Username" aria-label="Username"
                               aria-describedby="addon-wrapping" style="border: 0!important;">
                    </div>
                    <div style="display: flex;align-items: center">
                        <span style="display: flex;align-content: center">
                            <i class="bi bi-search"></i>
                        </span>
                    </div>
                </div>


                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>