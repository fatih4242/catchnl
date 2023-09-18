//
//  TopBarView.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import SwiftUI

struct TopBarView: View {
    var body: some View {
        NavigationView {
            HStack(alignment: .center){
                Spacer()
                Image("logo-dark")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 200)
                
                Spacer()
                
                NavigationLink {
                    EmptyView()
                } label: {
                    Image(systemName: "info.circle")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 40, height: 40)
                        .padding(.bottom, 10)
                        .foregroundColor(Color("secondry-color"))
                }

                
            }
            .padding()
        }
    }
}

struct TopBarView_Previews: PreviewProvider {
    static var previews: some View {
        TopBarView()
            .previewLayout(.sizeThatFits)
    }
}
