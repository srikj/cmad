<div id="createPost">
	<h2>New Post</h2>
	<div class="alert " role="alert"></div>
	<form class="form-horizontal" data-toggle="validator">
		<div class="form-group">
			<label for="postTitle" class="col-sm-2 control-label">Title</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="postTitle"
					placeholder="Title" required>
				<div class="help-block with-errors"></div>
			</div>
		</div>
		<div class="form-group">
			<label for="postTags" class="col-sm-2 control-label">Tags</label>
			<div class="col-sm-10">
				<input type="text" class="form-control full-width" id="postTags"
					placeholder="Tags" data-role="tagsinput">
			</div>
		</div>
		<div class="form-group">
			<label for="postTopic" class="col-sm-2 control-label">Topic</label>
			<div class="col-sm-10">
				<select id="postTopic" class="form-control">
					<option value="0">Technology</option>
					<option value="1">Movies</option>
					<option value="2">Sports</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="postText" class="col-sm-2 control-label">Post</label>
			<div class="col-sm-10">
				<textarea id="postText" class="form-control" rows="5"
					data-minlength="100"></textarea>
				<div class="help-block with-errors"></div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Submit Post</button>
			</div>
		</div>
	</form>
</div>