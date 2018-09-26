
(function($) {
	$.fn.extend({
		lSelect: function(options) {
			var settings = {
				choose: "请选择...",
				emptyValue: "",
				cssStyle: {"margin-right": "4px"},
				url: null,
				type: "GET"
			};
			$.extend(settings, options);
			
			var cache = {};
			return this.each(function() {
				var lSelectId = new Date().getTime();
				var $input = $(this);
				var treePath = $input.attr("treePath");
				var count = $input.attr("count");
				var url = $input.attr("url");
				if(url==undefined){
                    url=settings.url;
				}

				if (treePath != null && treePath != "") {
					var ids = (treePath + $input.val() + ",").split(",");
					var $position = $input;
					for (var i = 1; i < ids.length; i ++) {
						if(i>count) break;
						$position = addSelect($position, ids[i - 1], ids[i]);
					}
				} else {
					addSelect($input, null, null);
				}
				
				function addSelect($position, parentId, currentId) {
					$position.nextAll("select[name=" + lSelectId + "]").remove();
					if ($position.is("select") && (parentId == null || parentId == "")) {
						return false;
					}
					if (cache[parentId] == null) {
						$.ajax({
							url: url,
							type: settings.type,
							data: parentId != null ? {parentId: parentId} : null,
							dataType: "json",
							cache: false,
							async: false,
							success: function(data) {
								cache[parentId] = data;
							}
						});
					}
					var data = cache[parentId];
					if ($.isEmptyObject(data)) {
						return false;
					}
					var select = '<select count="'+count+ '" class="select form-control select-group" style="width:123px;display:inline;" name="' + lSelectId + '">';
					if (settings.emptyValue != null && settings.choose != null) {
						select += '<option value="' + settings.emptyValue + '">' + settings.choose + '</option>';
					}
					$.each(data, function(i, option) {
						if(option.value == currentId) {
							select += '<option value="' + option.value + '" selected="selected">' + option.name + '</option>';
						} else {
							select += '<option value="' + option.value + '">' + option.name + '</option>';
						}
					});
					select += '</select>';
					return $(select).css(settings.cssStyle).insertAfter($position).on("change", function() {
						var $this = $(this);
                        var count = $this.attr("count");
						var curCount = $this.prevAll().length;
						if ($this.val() == "") {
							var $prev = $this.prev("select[name=" + lSelectId + "]");
							if ($prev.size() > 0) {
								$input.val($prev.val());
							} else {
								$input.val(settings.emptyValue);
							}
						} else {
							$input.val($this.val());
						}
                        if(count!=undefined&&curCount>=count){
                            return;
                        }
						addSelect($this, $this.val(), null);
					});
				}
			});
			
		}
	});
})(jQuery);