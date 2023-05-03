package ua.southpost.resource.backup.web.model.menu;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstraction of catalog menu
 * Created by mykola on 14.10.16.
 */
public class CatalogMenu extends AbstractMenu {
	private final String catalogId;
	private List<AbstractMenu> submenuList;

	CatalogMenu(String title, String catalogId) {
		super(title);
		this.catalogId = catalogId;
	}

	public List<AbstractMenu> getSubmenuList() {
		return submenuList;
	}

	public void setSubmenuList(List<AbstractMenu> submenuList) {
		this.submenuList = submenuList;
	}

	CatalogMenu addSubMenu(AbstractMenu submenu) {
		if (submenu != null && submenu != this) {
			if (submenuList == null) {
				submenuList = new ArrayList<>();
			}
			submenuList.add(submenu);
			submenu.setLevel(1 + getLevel());
		}
		return this;
	}

	public String getCatalogId() {
		return catalogId;
	}

	@Override
	public void setLevel(int level) {
		super.setLevel(level);
		Optional.ofNullable(submenuList).orElse(Collections.emptyList()).forEach(m -> m.setLevel(level + 1));
	}

	private List<CatalogMenu> getSubCatalogs() {
		return Optional.ofNullable(submenuList).orElse(Collections.emptyList())
				.stream().filter(m -> m instanceof CatalogMenu)
				.map(CatalogMenu.class::cast)
				.collect(Collectors.toList());
	}

	public List<CatalogMenu> getCatalogsQueue() {
		final List<CatalogMenu> subCatalogs = getSubCatalogs();
		List<CatalogMenu> result = new ArrayList<>(subCatalogs);
		subCatalogs.forEach(c -> result.addAll(c.getCatalogsQueue()));
		return result.stream().sorted(Comparator.comparing(AbstractMenu::getLevel)).collect(Collectors.toList());
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled()
				&& Optional.ofNullable(submenuList).orElse(Collections.emptyList())
				.stream().anyMatch(AbstractMenu::isEnabled);
	}

	public Optional<ActionMenu> findAction(String actionUri) {
		return Optional.ofNullable(submenuList).orElse(Collections.emptyList()).stream()
				.map(m -> mapToUrinMenu(m, actionUri))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();
	}

	private Optional<ActionMenu> mapToUrinMenu(AbstractMenu menu, String uri) {
		Optional<ActionMenu> actionMenu = Optional.of(menu)
				.filter(m -> m instanceof ActionMenu)
				.map(m -> (ActionMenu) m)
				.filter(a -> StringUtils.equals(a.getActionUri(), uri));
		if (actionMenu.isPresent()) {
			return actionMenu;
		}
		actionMenu = Optional.of(menu).filter(m -> m instanceof CatalogMenu)
				.map(m -> (CatalogMenu) m)
				.map(m -> m.findAction(uri))
				.filter(Optional::isPresent)
				.map(Optional::get);
		return actionMenu;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CatalogMenu that = (CatalogMenu) o;

		return Objects.equals(submenuList, that.submenuList);

	}

	@Override
	public int hashCode() {
		return submenuList != null ? submenuList.hashCode() : 0;
	}
}
