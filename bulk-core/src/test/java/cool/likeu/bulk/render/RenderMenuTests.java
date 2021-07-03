package cool.likeu.bulk.render;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import cool.likeu.bulk.utils.JacksonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RenderMenuTests {

	private final static String MENU_JSON = "C:/Users/MORTAT/Desktop/menu.json";

	private final Path menuPath = Paths.get(MENU_JSON);

	private byte[] metaMenuJsonBytes;

	private final List<RenderMenu> renderMenuList = new ArrayList<>();

	@BeforeEach
	public void init() {
		try {
			this.metaMenuJsonBytes = Files.readAllBytes(menuPath);
		} catch (IOException e) {
			throw new RuntimeException("Cannot read menu.json", e);
		}
	}

	@Test
	void convertMenu2Bean() {
		RenderResponse<List<RenderMenu>> renderResponse = JacksonUtils.toObj(
				metaMenuJsonBytes, new RenderResponseTypeReference());
		assertNotNull(renderResponse);

		render(renderResponse.getResult());

		assertNotNull(renderMenuList);

		System.out.println(renderMenuList);
	}

	void render(List<RenderMenu> waitRenderMenuList) {
		for (RenderMenu renderMenu : waitRenderMenuList) {
			if (renderMenu.getParentId() == 0) {
				List<RenderMenu> childMenu = lookupChildMenu(waitRenderMenuList, renderMenu.getId());
				renderMenu.setChildMenu(childMenu);
				renderMenuList.add(renderMenu);
			}
		}
	}

	List<RenderMenu> lookupChildMenu(List<RenderMenu> waitRenderMenuList, int menuId) {
		final List<RenderMenu> childMenu = new ArrayList<>();
		for (RenderMenu renderMenu : waitRenderMenuList) {
			if (renderMenu.getParentId() == 0) continue;
			if (renderMenu.getParentId() == menuId) {
				childMenu.add(renderMenu);
			}
		}
		return childMenu;
	}

	static class RenderResponseTypeReference extends TypeReference<RenderResponse<List<RenderMenu>>> {

	}

	@Data
	static class RenderResponse<T> {
		private Integer code;
		private String message;
		private T result;
		private Long timestamp;
	}

	@Data
	@Accessors(chain = true)
	static class RenderMenu {

		private Integer id;
		private Integer parentId;
		private String name;

		/**
		 * 对应前端的组件 {@code @/view/component}
		 */
		private String component;

		/**
		 * 对应前端，是否有重定向跳转
		 */
		private String redirect;

		/**
		 * 元数据
		 */
		private Meta meta;

		private List<RenderMenu> childMenu;

		@Data
		static class Meta {
			private String icon;
			private String title;
			private String target;
			private boolean show;
			private boolean hideHeader;
			private boolean hideChildren;
			private boolean hiddenHeaderContent;
		}
	}
}
