package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.repository.CatalogRepository;

/**
 * Provide Category service for managing category easier. Ex. add, edit, delete,
 * find.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("catalogService")
@Transactional
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogRepository catalogRepository;

	@Override
	public Catalog findByKey(Integer id) {
		return catalogRepository.findByKey(id);
	}

	@Override
	public void store(Catalog catalog) {
		catalogRepository.store(catalog);

	}

	@Override
	public void update(Catalog catalog) {
		Catalog entity = catalogRepository.findByKey(catalog.getId());
		if (entity != null) {
			entity.setCategory(catalog.getCategory());
			entity.setProductDescription(catalog.getProductDescription());
			entity.setSubCategory(catalog.getSubCategory());
		}

	}

	@Override
	public void removeById(Integer id) {
		catalogRepository.remove(id);
	}

	@Override
	public List<Catalog> findAll() {
		return catalogRepository.findAll();
	}

	@Override
	public List<Catalog> findAndAscByName(int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Catalog> findAndDescByName(int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Catalog> findByCategory(Category category) {
		return catalogRepository.findByCategory(category);
	}

	@Override
	public List<Catalog> findByCategoryAndSubCategory(Category category, SubCategory subcategory) {
		return catalogRepository.findByCategoryAndSubCategory(category, subcategory);
	}

}
