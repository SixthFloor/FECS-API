package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;
import th.in.nagi.fecs.repository.CatalogRepository;
import th.in.nagi.fecs.repository.TypeRepository;

/**
 * Provide Catalog service for managing easier. Ex. add, edit, delete, find.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("catalogService")
@Transactional
public class CatalogService {

	/**
	 * Tool for managing catalog
	 */
	@Autowired
	private CatalogRepository catalogRepository;

	/**
	 * Tool for managing type
	 */
	@Autowired
	private TypeRepository typeRepository;

	/**
	 * Find catalog by using id.
	 * 
	 * @param id
	 * @return Catalog
	 */
	public Catalog findByKey(Integer id) {
		return catalogRepository.findByKey(id);
	}

	/**
	 * Save catalog in database.
	 * 
	 * @param catalog
	 *            new catalog
	 */
	public void store(Catalog catalog) {
		catalogRepository.store(catalog);

	}

	/**
	 * Update catalog's detail in database.
	 * 
	 * @param catalog
	 *            old catalog
	 */
	public void update(Catalog catalog) {
		Catalog entity = catalogRepository.findByKey(catalog.getId());
		if (entity != null) {
			if (catalog.getType() != null) {
				entity.setType(catalog.getType());
			}
			if (catalog.getProductDescription() != null) {
				entity.setProductDescription(catalog.getProductDescription());
			}
		}

	}

	public void removeById(Integer id) {
		catalogRepository.remove(id);
	}

	/**
	 * Find all catalogs in database.
	 * 
	 * @return List<Catalog>
	 */
	public List<Catalog> findAll() {
		return catalogRepository.findAll();
	}

	public List<Catalog> findAndAscByName(int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Catalog> findAndDescByName(int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create new catalogs in database.
	 * 
	 * @param category
	 * @param subCategory
	 * @param productDescription
	 * 
	 * @return true if it is successful.
	 */
	public boolean createCatalog(Category category, SubCategory subCategory, ProductDescription productDescription) {
		Type type = typeRepository.findByCategoryAndSubCategory(category, subCategory);
		if (type == null) {
			return false;
		}
		Catalog catalog = new Catalog();
		catalog.setType(type);
		catalog.setProductDescription(productDescription);
		this.store(catalog);
		return true;
	}

	/**
	 * Create new catalogs in database.
	 * 
	 * @param type
	 * @param productDescription
	 * 
	 * @return true if it is successful.
	 */
	public boolean createCatalog(Type type, ProductDescription productDescription) {
		if (type == null) {
			return false;
		}
		Catalog catalog = new Catalog();
		catalog.setType(type);
		catalog.setProductDescription(productDescription);
		this.store(catalog);
		return true;
	}

	/**
	 * Get product description
	 * 
	 * @param type
	 * 
	 * @return List<ProductDescription>
	 */
	public List<ProductDescription> findProductByType(Type type) {
		List<ProductDescription> productDescriptions = new ArrayList<>();
		for (Catalog catalog : catalogRepository.findByType(type)) {
			productDescriptions.add(catalog.getProductDescription());
		}
		return productDescriptions;

	}

	/**
	 * Get product's type
	 * 
	 * @param productDescription
	 * 
	 * @return List<ProductDescription>
	 */
	public List<Type> findTypeByProduct(ProductDescription productDescription) {
		List<Type> type = new ArrayList<>();
		for (Catalog catalog : catalogRepository.findByProduct(productDescription)) {
			type.add(catalog.getType());
		}
		return type;
	}

	/**
	 * Get catalogs
	 * 
	 * @param productDescription
	 * 
	 * @return List<Catalog>
	 */
	public List<Catalog> findCatalogByProduct(ProductDescription productDescription) {
		List<Catalog> catalog = catalogRepository.findByProduct(productDescription);
		return catalog;
	}

}
