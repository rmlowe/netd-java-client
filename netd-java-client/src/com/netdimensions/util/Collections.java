/*
 *
 * Copyright (c) 1999-2012 NetDimensions Ltd.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * NetDimensions Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with NetDimensions.
 */
package com.netdimensions.util;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.List;
import java.util.ListIterator;

public final class Collections {
	private static final class MappedList<T, F> extends AbstractList<T>
			implements Serializable {
		private static final long serialVersionUID = 1L;
		private final Function<F, T> f;
		private final List<F> list;

		private MappedList(final List<F> list, final Function<F, T> f) {
			this.list = list;
			this.f = f;
		}

		@Override
		public final T get(final int index) {
			return f.value(list.get(index));
		}

		@Override
		public final int size() {
			return list.size();
		}
	}

	private static final class ConcatenatedList<T> extends
			AbstractSequentialList<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		private final List<? extends T> xs;
		private final List<? extends T> ys;

		private ConcatenatedList(final List<? extends T> xs,
				final List<? extends T> ys) {
			this.xs = xs;
			this.ys = ys;
		}

		@Override
		public final ListIterator<T> listIterator(final int index) {
			return new ConcatenatedListIterator<T>(xs.listIterator(index < xs
					.size() ? index : xs.size()), ys.listIterator(index < xs
					.size() ? 0 : (index - xs.size())));
		}

		@Override
		public final int size() {
			return xs.size() + ys.size();
		}
	}

	private static final class ConcatenatedListIterator<T> implements
			ListIterator<T>, Serializable {
		private static final long serialVersionUID = 1L;
		private final ListIterator<? extends T> xsi;
		private final ListIterator<? extends T> ysi;

		private ConcatenatedListIterator(final ListIterator<? extends T> xsi,
				final ListIterator<? extends T> ysi) {
			this.xsi = xsi;
			this.ysi = ysi;
		}

		@Override
		public final boolean hasNext() {
			return xsi.hasNext() || ysi.hasNext();
		}

		@Override
		public final T next() {
			return xsi.hasNext() ? xsi.next() : ysi.next();
		}

		@Override
		public final boolean hasPrevious() {
			return ysi.hasPrevious() || xsi.hasPrevious();
		}

		@Override
		public final T previous() {
			return ysi.hasPrevious() ? ysi.previous() : xsi.previous();
		}

		@Override
		public final int nextIndex() {
			return xsi.nextIndex() + ysi.nextIndex();
		}

		@Override
		public final int previousIndex() {
			return xsi.previousIndex() + ysi.previousIndex() + 1;
		}

		@Override
		public final void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final void set(final T e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final void add(final T e) {
			throw new UnsupportedOperationException();
		}
	}

	// Suppress default constructor for noninstantiability
	private Collections() {
		throw new AssertionError();
	}

	public static <T> List<T> concatenatedList(final List<? extends T> xs,
			final List<? extends T> ys) {
		return new ConcatenatedList<T>(xs, ys);
	}

	public static <F, T> List<T> mappedList(final List<F> list,
			final Function<F, T> f) {
		return new MappedList<T, F>(list, f);
	}
}
