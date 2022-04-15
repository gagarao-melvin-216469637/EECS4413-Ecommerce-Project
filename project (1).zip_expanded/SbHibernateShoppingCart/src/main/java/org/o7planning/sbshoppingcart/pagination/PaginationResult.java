package org.o7planning.sbshoppingcart.pagination;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;

public class PaginationResult<E> {

	private int allRecords;
	private int currentPage;
	private List<E> elements;
	private int maxRes;
	private int totalPages;
	private int maxNavPage;
	private List<Integer> navPages;

	// Constructor
	public PaginationResult(Query<E> query, int page, int maxRes, int maxNavPage) {

		// initialize the value of pageIndex
		// decrement page if possible
		int pageIndex;
		if (page <= 0)
			pageIndex = 0;
		else
			pageIndex = page - 1;

		int maxRecordIndex = (pageIndex * maxRes) + maxRes;

		// Allows scrolling through large amounts of data without updating
		// Insensitive to changes in underlying data
		ScrollableResults resScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
		List<E> res = new ArrayList<>();

		// initialize the flag for checking results
		boolean hasRes = resScroll.first();

		if (hasRes) {
			// scroll to element
			hasRes = resScroll.scroll(pageIndex * maxRes);

			if (hasRes) {
				// add the 1st element if possible
				// verify a next element exist
				do {
					@SuppressWarnings("unchecked") // supressing cast warnings
					E record = (E) resScroll.get(0);
					res.add(record);
				} while (resScroll.next() && resScroll.getRowNumber() >= pageIndex * maxRes
						&& resScroll.getRowNumber() < maxRecordIndex);

			}

			// go to last record
			resScroll.last();
		}

		// Total Records
		allRecords = resScroll.getRowNumber() + 1;
		currentPage = pageIndex + 1;
		elements = res;
		this.maxRes = maxRes;

		// divide records by results
		if (this.allRecords % this.maxRes == 0)
			this.totalPages = this.allRecords / this.maxRes;
		else
			this.totalPages = (this.allRecords / this.maxRes) + 1;

		// set maximum navigation page
		this.maxNavPage = maxNavPage;

		// set maximum navigation page again...
		if (maxNavPage < totalPages)
			this.maxNavPage = maxNavPage;

		this.calcNavigationPages();
	}

	private void calcNavigationPages() {

		// reset navigation pages
		this.navPages = new ArrayList<Integer>();

		// this.currentPage;
		// current page remains the same unless it's greater than total pages
		int current;
		if (this.currentPage > this.totalPages)
			current = this.totalPages;
		else
			current = this.currentPage;

		// initialize boundaries for page navigation
		// width of maxNavigationPage
		int begin = current - this.maxNavPage / 2;
		int end = current + this.maxNavPage / 2;

		// add the 1st page
		navPages.add(1);

		// has to at least be 2 to start
		if (begin > 2)
			navPages.add(-1);

		// add pages with range
		for (int i = begin; i < end; i++)
			if (i > 1 && i < this.totalPages)
				navPages.add(i);

		if (end < this.totalPages - 2)
			navPages.add(-1);

		// add the last page
		navPages.add(this.totalPages);
	}

	/**
	 * getter for the amount of pages
	 * 
	 * @return total amount of pages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * getter for all records
	 * 
	 * @return total amount of records
	 */
	public int getTotalRecords() {
		return allRecords;
	}

	/**
	 * getter for current page
	 * 
	 * @return the current page number
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * getter for shallow copy of List<E>
	 * 
	 * @return list of
	 */
	public List<E> getList() {
		return elements;
	}

	/**
	 * getter for max result
	 * 
	 * @return the max result
	 */
	public int getMaxResult() {
		return maxRes;
	}

	/**
	 * getter for shallow copy of navigation pages
	 * 
	 * @return the navigation pages
	 */
	public List<Integer> getNavigationPages() {
		return navPages;
	}

}